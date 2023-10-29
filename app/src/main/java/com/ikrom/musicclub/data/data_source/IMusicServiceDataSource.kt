package com.ikrom.musicclub.data.data_source

import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import kotlinx.coroutines.flow.StateFlow

interface IMusicServiceDataSource {
    fun getTracksByQuery(query: String): StateFlow<List<Track>>
    fun getNewReleaseAlbums(): StateFlow<List<Album>>
    fun getAlbumTracks(albumId: String): StateFlow<List<Track>>
}