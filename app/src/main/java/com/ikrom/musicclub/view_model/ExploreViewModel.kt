package com.ikrom.musicclub.view_model

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikrom.musicclub.data.model.SearchHistory
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(private val repository: MusicServiceRepository) : ViewModel() {
    private val _requestList = MutableStateFlow(emptyList<Track>())
    var requestList = _requestList.asStateFlow()

    private val _searchHistory = MutableStateFlow(emptyList<SearchHistory>())
    var searchHistory = _searchHistory.asStateFlow()

    var userInput = ""

    init {
        updateSearchHistory(userInput)
    }

    fun search(query: String = userInput) {
        if (query.isNotBlank()){
            viewModelScope.launch{
                repository.getTracksByQuery(query).collect{
                    _requestList.value = it
                }
            }
            viewModelScope.launch {
                repository.addToSearchHistory(SearchHistory(query = query))
            }
        }
    }

    fun updateSearchHistory(query: String){
        viewModelScope.launch {
            repository.getSearchHistoryList(query).collect {
                _searchHistory.value = it
            }
        }
    }

    fun deleteSearchHistory(query: String){
        viewModelScope.launch {
            repository.deleteFromSearchHistory(query)
            updateSearchHistory(query)
        }
    }
}