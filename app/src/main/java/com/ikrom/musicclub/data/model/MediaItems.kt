package com.ikrom.musicclub.data.model


data class Track(
    val title: String,
    val videoId: String,
    val album: Album,
)

data class Album(
    val id: String,
    val title: String,
    val artists: List<Artist>?,
    val cover: String,
    val year: Int?
) {
    val shareLink: String
        get() = "https://music.youtube.com/playlist?list=$id"
}

data class Artist(
    val id: String?,
    val name: String
)