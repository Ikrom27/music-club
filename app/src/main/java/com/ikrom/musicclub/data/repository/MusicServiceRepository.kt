package com.ikrom.musicclub.data.repository

import com.ikrom.musicclub.data.data_source.IMusicServiceDataSource
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MusicServiceRepository @Inject constructor(
    private val youtubeService: IMusicServiceDataSource
) {
    fun getTracksByQuery(query: String): StateFlow<List<Track>>{
        return youtubeService.getTracksByQuery(query)
    }

    fun getNewReleases(): StateFlow<List<Album>> {
        return youtubeService.getNewReleaseAlbums()
    }

    fun getAlbumTracks(albumId: String): StateFlow<List<Track>> {
        return youtubeService.getAlbumTracks(albumId)
    }
}

//"MPREb_ebWSmYnO2aa" meteora