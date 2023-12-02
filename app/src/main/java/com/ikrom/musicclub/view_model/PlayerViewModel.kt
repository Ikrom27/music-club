package com.ikrom.musicclub.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import com.ikrom.musicclub.extensions.toMediaItem
import com.ikrom.musicclub.playback.ExoDownloadService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val player: ExoPlayer,
    val repository: MusicServiceRepository
): ViewModel() {
    private var currentMediaItem = mutableStateOf(player.currentMediaItem)
    var currentTrack: Track? = null
    val playbackState = MutableStateFlow(player.playbackState)
    val isPlaying = MutableStateFlow(player.playWhenReady)
    var currentPosition = mutableLongStateOf(0L)
    var totalDuration =  mutableLongStateOf(0L)

    init {
        Log.d("PlayerViewModel", "FUUUCK")
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
    @UnstableApi
    fun addToFavorite(context: Context){
        viewModelScope.launch {
            currentTrack?.let {
                val downloadRequest = DownloadRequest.Builder(it.videoId, it.videoId.toUri())
                    .setCustomCacheKey(it.videoId)
                    .setData(it.title.toByteArray())
                    .build()
                DownloadService.sendAddDownload(
                    context,
                    ExoDownloadService::class.java,
                    downloadRequest,
                    false
                )
                repository.addToFavorite(it)
            }
        }
    }

    fun playNow(track: Track){
        currentTrack = track
        if (player.currentMediaItem != track.toMediaItem()){
            player.clearMediaItems()
            player.setMediaItems(listOf(track.toMediaItem()))
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

    fun getCurrentMediaItem(): MutableState<MediaItem?> {
        return currentMediaItem
    }
}