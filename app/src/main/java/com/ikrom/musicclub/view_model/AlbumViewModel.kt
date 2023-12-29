package com.ikrom.musicclub.view_model

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
class AlbumViewModel @Inject constructor(
    private val repository: MusicServiceRepository
): ViewModel() {
    var currentAlbum: Album? = null
        set(value) {
            field = value
            viewModelScope.launch {
                repository.getAlbumTracks(value!!.id).collect{
                    _albumTracks.value = it
                }
            }
        }

    private val _albumTracks = MutableStateFlow<List<Track>>(emptyList())
    val albumTracks = _albumTracks.asStateFlow()
}