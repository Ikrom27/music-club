package com.ikrom.musicclub.data.data_source

import com.ikrom.musicclub.data.model.SearchHistory
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.data.model.TrackEntity
import com.ikrom.musicclub.data.room.AppDao
import com.ikrom.musicclub.data.room.AppDataBase
import com.ikrom.musicclub.extensions.toTrack
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LocalDataSource @Inject constructor(dataBase: AppDataBase) {
    private val appDao: AppDao = dataBase.dao()
    suspend fun insertTrack(track: TrackEntity) {
        appDao.insertTrack(track)
    }

    suspend fun isFavorite(id: String): Boolean{
        return appDao.countById(id) > 0
    }

    suspend fun getAllTracks(): MutableStateFlow<List<Track>> {
        return MutableStateFlow(appDao.getAllTracks().map {it.toTrack() })
    }

    suspend fun deleteTrackById(id: String){
        appDao.deleteTrackById(id)
    }

    suspend fun addQueryToHistory(searchHistory: SearchHistory){
        appDao.insertSearchHistory(searchHistory)
    }

    suspend fun getSearchHistoryList(query: String = ""): MutableStateFlow<List<SearchHistory>> {
        return MutableStateFlow(appDao.getSearchHistoryList(query))
    }

    suspend fun deleteSearchHistory(query: String){
        appDao.deleteSearchHistory(query)
    }
}