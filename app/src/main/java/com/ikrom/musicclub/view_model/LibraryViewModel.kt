package com.ikrom.musicclub.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: MusicServiceRepository
) : ViewModel() {

    private val _favoriteTracks = MutableStateFlow(emptyList<Track>())
    var favoriteTracks = _favoriteTracks.asStateFlow()

    fun getFavoriteTracks(){
        viewModelScope.launch {
            repository.getFavoriteTracks().collect{
                _favoriteTracks.value = it
            }
        }
    }
}