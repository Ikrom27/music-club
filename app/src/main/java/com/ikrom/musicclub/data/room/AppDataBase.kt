package com.ikrom.musicclub.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ikrom.musicclub.data.model.SearchHistory
import com.ikrom.musicclub.data.model.TrackEntity

@Database(entities = [TrackEntity::class, SearchHistory::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao(): AppDao
}
