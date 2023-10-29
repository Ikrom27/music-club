package com.ikrom.musicclub.data.data_source

import android.util.Log
import com.ikrom.innertube.YouTube
import com.ikrom.innertube.models.SongItem
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.extensions.toAlbum
import com.ikrom.musicclub.extensions.toTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class YoutubeMusicDataSource: IMusicServiceDataSource {
    private val TAG = "YoutubeMusicDataSource"
    override fun getTracksByQuery(query: String): StateFlow<List<Track>> {
        val tracksStateFlow = MutableStateFlow<List<Track>>(ArrayList())
        CoroutineScope(Dispatchers.IO).launch {
            YouTube.search(query, YouTube.SearchFilter.FILTER_SONG).onSuccess { result ->
                tracksStateFlow.value = result.items.map { (it as SongItem).toTrack()}
            }.onFailure {
                Log.e(TAG, "onFailure error: $it")
            }
        }
        return tracksStateFlow
    }

    override fun getNewReleaseAlbums(): StateFlow<List<Album>> {
        val responseStateFlow = MutableStateFlow<List<Album>>(ArrayList())
        CoroutineScope(Dispatchers.IO).launch {
            YouTube.newReleaseAlbums().onSuccess { result ->
                responseStateFlow.value = result.map{ it.toAlbum() }
            }.onFailure {
                Log.e(TAG, "onFailure error: $it")
            }
        }
        return responseStateFlow
    }

    override fun getAlbumTracks(albumId: String): StateFlow<List<Track>> {
        val responseStateFlow = MutableStateFlow<List<Track>>(ArrayList())
        CoroutineScope(Dispatchers.IO).launch {
            YouTube.album(albumId).onSuccess { result ->
                responseStateFlow.value = result.songs.map { it.toTrack() }
            }
        }
        return responseStateFlow
    }
}