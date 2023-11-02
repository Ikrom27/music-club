package com.ikrom.musicclub.extensions

import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.ikrom.musicclub.data.model.Track


val Player.currentMetaData: MediaMetadata?
    get() = currentMediaItem?.mediaMetadata

fun Player.togglePlayPause() {
    playWhenReady = !playWhenReady
}

@OptIn(UnstableApi::class)
fun Track.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setMediaId(this.videoId)
        .setUri(this.videoId)
        .setCustomCacheKey(this.videoId)
        .setTag(this)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(this.title)
                .setArtist(this.album.artists.getNames())
                .setArtworkUri(this.album.cover.toUri())
                .build()
        )
        .build()
}