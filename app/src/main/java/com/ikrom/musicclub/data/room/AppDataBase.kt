package com.ikrom.musicclub.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ikrom.musicclub.data.model.TrackEntity

@Database(entities = [TrackEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao(): AppDao
}
