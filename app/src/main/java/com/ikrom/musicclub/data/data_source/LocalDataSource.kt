package com.ikrom.musicclub.data.data_source

import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.model.TrackEntity
import com.ikrom.musicclub.data.room.AppDao
import com.ikrom.musicclub.data.room.AppDataBase
import com.ikrom.musicclub.extensions.toAlbum
import com.ikrom.musicclub.extensions.toArtist
import com.ikrom.musicclub.extensions.toTrack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(dataBase: AppDataBase) {
    private val trackDao: AppDao = dataBase.dao()
    suspend fun insertTrack(track: TrackEntity) {
        trackDao.insertTrack(track)
    }

    suspend fun getAllTracks(): MutableStateFlow<List<Track>> {
        return MutableStateFlow(trackDao.getAllTracks().map {it.toTrack() })
    }
}