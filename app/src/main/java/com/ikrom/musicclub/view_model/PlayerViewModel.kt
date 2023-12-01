package com.ikrom.musicclub.view_model

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val player: ExoPlayer
): ViewModel() {
    private var currentMediaItem = MutableStateFlow(player.currentMediaItem)
    val playbackState = MutableStateFlow(player.playbackState)
    val isPlaying = MutableStateFlow(player.playWhenReady)
    var currentPosition = mutableLongStateOf(0L)
    var totalDuration =  mutableLongStateOf(0L)

    init {
        player.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                currentMediaItem.value = mediaItem
            }

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                isPlaying.value = playWhenReady
            }

            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                totalDuration.value = player.duration
                currentPosition.value = player.currentPosition
                Log.d("pos", currentPosition.toString())
            }
        })
    }

    fun playNow(item: MediaItem){
        if (player.currentMediaItem != item){
            player.clearMediaItems()
            player.setMediaItems(listOf(item))
            player.prepare()
            player.playWhenReady = true
        }
    }

    fun playNext(item: MediaItem){
        playNext(listOf(item))
    }

    fun playNext(items: List<MediaItem>) {
        player.addMediaItems(if (player.mediaItemCount == 0) 0 else player.currentMediaItemIndex + 1, items)
        player.prepare()
        player.playWhenReady = true
    }

    fun addToQueue(item: MediaItem) {
        addToQueue(listOf(item))
    }

    fun addToQueue(items: List<MediaItem>) {
        player.addMediaItems(items)
        player.prepare()
    }

    fun getCurrentMediaItem(): StateFlow<MediaItem?> {
        return currentMediaItem
    }
}