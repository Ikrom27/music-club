package com.ikrom.musicclub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val videoId: String,
    val title: String,
    val albumId: String,
    val albumTitle: String,
    val cover: String,
    val year: Int?,
    val artistId: String,
    val artistName: String
)