package com.ikrom.musicclub.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.ikrom.musicclub.data.data_source.IMusicServiceDataSource
import com.ikrom.musicclub.data.data_source.LocalDataSource
import com.ikrom.musicclub.data.data_source.YoutubeMusicDataSource
import com.ikrom.musicclub.data.repository.MusicServiceRepository
import com.ikrom.musicclub.data.room.AppDataBase
import com.ikrom.musicclub.playback.MusicNotificationManager
import com.ikrom.musicclub.utils.MediaSourceFactory
import com.ikrom.musicclub.playback.MusicPlayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlayerCacheScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DownloadCacheScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class YoutubeDataSource


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMusicServiceRepository(
        @YoutubeDataSource youtubeMusicDataSource: IMusicServiceDataSource,
        localDataSource: LocalDataSource
    ): MusicServiceRepository = MusicServiceRepository(youtubeMusicDataSource, localDataSource)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        dataBase: AppDataBase
    ): LocalDataSource = LocalDataSource(dataBase)

    @Provides
    @Singleton
    @YoutubeDataSource
    fun provideYoutubeDataSource(): IMusicServiceDataSource = YoutubeMusicDataSource()

    @Provides
    @Singleton
    fun providePlayer(
        @ApplicationContext context: Context,
        mediaSourceFactory: DefaultMediaSourceFactory
    ) : ExoPlayer = ExoPlayer.Builder(context)
        .setMediaSourceFactory(mediaSourceFactory)
        .build()

    @Provides
    @Singleton
    fun provideMediaSourceFactory(
        @ApplicationContext context: Context,
        @DownloadCacheScope
        downloadCache: SimpleCache,
        @PlayerCacheScope
        playerCache: SimpleCache
    ) : DefaultMediaSourceFactory
            = MediaSourceFactory(downloadCache, playerCache).createMediaSourceFactory(context)

    @Singleton
    @Provides
    @OptIn(UnstableApi::class)
    fun provideDatabaseProvider(@ApplicationContext context: Context): DatabaseProvider =
        StandaloneDatabaseProvider(context)

    @Provides
    @Singleton
    @PlayerCacheScope
    @OptIn(UnstableApi::class)
    fun providePlayerCache(@ApplicationContext context: Context, databaseProvider: DatabaseProvider)
            = SimpleCache(
        context.filesDir.resolve("player"),
        NoOpCacheEvictor(),
        databaseProvider)

    @Provides
    @Singleton
    @DownloadCacheScope
    @OptIn(UnstableApi::class)
    fun provideDownloadCache(@ApplicationContext context: Context, databaseProvider: DatabaseProvider)
            = SimpleCache(
        context.filesDir.resolve("download"),
        NoOpCacheEvictor(),
        databaseProvider)

    @Provides
    @Singleton
    fun provideMusicPlayerService(): MusicPlayerService = MusicPlayerService()

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context,
        player: ExoPlayer
    ) : MusicNotificationManager = MusicNotificationManager(context, player)
}
