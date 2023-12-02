package com.ikrom.musicclub.data.data_source

import androidx.compose.runtime.MutableState
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IMusicServiceDataSource {
    fun getTracksByQuery(query: String): MutableStateFlow<List<Track>>
    fun getNewReleaseAlbums(): MutableStateFlow<List<Album>>
    fun getAlbumTracks(albumId: String): MutableState<List<Track>>
}