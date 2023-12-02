package com.ikrom.musicclub.data.repository

import androidx.compose.runtime.MutableState
import com.ikrom.musicclub.data.data_source.IMusicServiceDataSource
import com.ikrom.musicclub.data.data_source.LocalDataSource
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.model.TrackEntity
import com.ikrom.musicclub.extensions.toTrackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MusicServiceRepository @Inject constructor(
    private val youtubeService: IMusicServiceDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getTracksByQuery(query: String): MutableStateFlow<List<Track>> {
        return youtubeService.getTracksByQuery(query)
    }

    fun getNewReleases(): MutableStateFlow<List<Album>> {
        return youtubeService.getNewReleaseAlbums()
    }

    fun getAlbumTracks(albumId: String): MutableState<List<Track>> {
        return youtubeService.getAlbumTracks(albumId)
    }

    suspend fun getFavoriteTracks(): MutableStateFlow<List<Track>>{
        return localDataSource.getAllTracks()
    }

    suspend fun addToFavorite(track: Track){
        localDataSource.insertTrack(track.toTrackEntity())
    }
}