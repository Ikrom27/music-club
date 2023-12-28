package com.ikrom.musicclub.view_model

import androidx.lifecycle.ViewModel
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val repository: MusicServiceRepository
): ViewModel() {
    var currentAlbum: Album? = null
    private val _albumTracks = MutableStateFlow<List<Track>>(emptyList())
    val albumTracks = _albumTracks.asStateFlow()

    fun loadTracks(){
        currentAlbum?.let {
            _albumTracks.value = repository.getAlbumTracks(it.id).value
        }
    }
}