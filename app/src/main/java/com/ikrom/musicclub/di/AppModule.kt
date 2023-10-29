package com.ikrom.musicclub.di

import com.ikrom.musicclub.data.data_source.IMusicServiceDataSource
import com.ikrom.musicclub.data.data_source.YoutubeMusicDataSource
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class YoutubeDataSource


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMusicServiceRepository(
        @YoutubeDataSource youtubeMusicDataSource: IMusicServiceDataSource
    ): MusicServiceRepository = MusicServiceRepository(youtubeMusicDataSource)

    @Provides
    @Singleton
    @YoutubeDataSource
    fun provideYoutubeDataSource(): IMusicServiceDataSource = YoutubeMusicDataSource()
}