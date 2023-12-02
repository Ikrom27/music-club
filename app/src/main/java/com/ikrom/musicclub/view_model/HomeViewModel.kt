package com.ikrom.musicclub.view_model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MusicServiceRepository
): ViewModel() {
    fun getTracksByQuery(query: String): MutableStateFlow<List<Track>> {
        return repository.getTracksByQuery(query)
    }

    fun getNewRelease(): MutableState<List<Album>>{
        return repository.getNewReleases()
    }
}