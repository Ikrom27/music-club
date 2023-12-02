package com.ikrom.musicclub.extensions

import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Artist
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.model.TrackEntity



fun TrackEntity.toTrack(): Track {
    return Track(
        title = this.title,
        videoId = this.videoId,
        album = Album(
            this.albumId,
            this.albumTitle,
            listOf(Artist(
                this.artistId,
                this.artistName
            )),
            this.cover,
            this.year
        )
    )
}
