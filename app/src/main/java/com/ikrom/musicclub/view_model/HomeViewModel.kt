package com.ikrom.musicclub.view_model

import androidx.lifecycle.ViewModel
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MusicServiceRepository
): ViewModel() {
    fun getTracksByQuery(query: String): StateFlow<List<Track>>{
        return repository.getTracksByQuery(query)
    }

    fun getNewRelease(): StateFlow<List<Album>>{
        return repository.getNewReleases()
    }
}