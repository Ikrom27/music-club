package com.ikrom.musicclub.data.data_source

import androidx.compose.runtime.MutableState
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import kotlinx.coroutines.flow.StateFlow

interface IMusicServiceDataSource {
    fun getTracksByQuery(query: String): MutableState<List<Track>>
    fun getNewReleaseAlbums(): MutableState<List<Album>>
    fun getAlbumTracks(albumId: String): MutableState<List<Track>>
}