package com.ikrom.musicclub.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ikrom.musicclub.data.room.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ) : AppDataBase = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
        "music-club"
        ).build()
}