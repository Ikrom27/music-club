package com.ikrom.musicclub.extensions

import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.model.TrackEntity


fun Track.toTrackEntity(): TrackEntity {
    return TrackEntity(
        videoId = this.videoId,
        title = this.title,
        albumId = this.album.id,
        albumTitle = this.album.title,
        cover = this.album.cover,
        year = this.album.year,
        artistId = this.album.artists?.firstOrNull()?.id.orEmpty(),
        artistName = this.album.artists?.firstOrNull()?.name.orEmpty()
    )
}
