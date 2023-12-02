package com.ikrom.musicclub.view_model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MusicServiceRepository
): ViewModel() {
    private val _queryList = MutableStateFlow(emptyList<Track>())
    val queryList = _queryList.asStateFlow()

    private val _releaseList = MutableStateFlow(emptyList<Album>())
    val releaseList = _releaseList.asStateFlow()

    fun getTracksByQuery(query: String) {
        viewModelScope.launch {
            repository.getTracksByQuery(query).collect{
                _queryList.value = it
            }
        }
    }

    fun getNewRelease() {
        viewModelScope.launch {
            repository.getNewReleases().collect{
                _releaseList.value = it
            }
        }
    }
}