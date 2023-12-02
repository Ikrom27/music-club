package com.ikrom.musicclub.data.repository

import androidx.compose.runtime.MutableState
import com.ikrom.musicclub.data.data_source.IMusicServiceDataSource
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MusicServiceRepository @Inject constructor(
    private val youtubeService: IMusicServiceDataSource
) {
    fun getTracksByQuery(query: String): MutableStateFlow<List<Track>> {
        return youtubeService.getTracksByQuery(query)
    }

    fun getNewReleases(): MutableState<List<Album>> {
        return youtubeService.getNewReleaseAlbums()
    }

    fun getAlbumTracks(albumId: String): MutableState<List<Track>> {
        return youtubeService.getAlbumTracks(albumId)
    }
}

//"MPREb_ebWSmYnO2aa" meteora