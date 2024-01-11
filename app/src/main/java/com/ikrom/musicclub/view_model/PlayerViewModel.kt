package com.ikrom.musicclub.view_model

import androidx.lifecycle.ViewModel
import com.ikrom.musicclub.playback.PlayerConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val playerConnection: PlayerConnection
): ViewModel() {

}