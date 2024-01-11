package com.ikrom.musicclub.data.data_source

import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import com.ikrom.innertube.models.SearchSuggestions

interface IMusicServiceDataSource {
    fun getTracksByQuery(query: String): MutableStateFlow<List<Track>>
    fun getNewReleaseAlbums(): MutableStateFlow<List<Album>>
    fun getAlbumTracks(albumId: String): MutableStateFlow<List<Track>>
    abstract fun getSearchSuggestions(query: String): MutableStateFlow<SearchSuggestions>
}