package com.ikrom.musicclub.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(private val repository: MusicServiceRepository) : ViewModel() {
    private val _requestList = MutableStateFlow(emptyList<Track>())
    var requestList = _requestList.asStateFlow()
    var userInput = ""

    fun search(query: String = userInput) {
        viewModelScope.launch{
            repository.getTracksByQuery(query).collect{
                _requestList.value = it
            }
        }
    }
}