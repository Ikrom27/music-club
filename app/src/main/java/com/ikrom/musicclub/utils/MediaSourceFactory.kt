package com.ikrom.musicclub.utils

import android.content.Context
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.PlaybackException
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.extractor.ExtractorsFactory
import androidx.media3.extractor.mkv.MatroskaExtractor
import androidx.media3.extractor.mp4.FragmentedMp4Extractor
import com.ikrom.innertube.YouTube
import com.ikrom.musicclub.di.DownloadCacheScope
import com.ikrom.musicclub.di.PlayerCacheScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@OptIn(UnstableApi::class)
class MediaSourceFactory(
    @DownloadCacheScope
    val downloadCache: SimpleCache,
    @PlayerCacheScope
    var playerCache: SimpleCache
) {
    private val CHUNK_LENGTH = 512 * 1024L


    fun createMediaSourceFactory(context: Context): DefaultMediaSourceFactory {
        return DefaultMediaSourceFactory(createDataSourceFactory(context), createExtractorsFactory())
    }

    private fun createExtractorsFactory() = ExtractorsFactory {
        arrayOf(MatroskaExtractor(), FragmentedMp4Extractor())
    }

    private fun createOkHttpDataSourceFactory() =
        OkHttpDataSource.Factory(
            OkHttpClient.Builder()
                .proxy(YouTube.proxy)
                .build()
        )

    private fun createCacheDataSource(context: Context): CacheDataSource.Factory {
        return CacheDataSource.Factory()
            .setCache(downloadCache)
            .setUpstreamDataSourceFactory(
                CacheDataSource.Factory()
                    .setCache(playerCache)
                    .setUpstreamDataSourceFactory(DefaultDataSource.Factory(context, createOkHttpDataSourceFactory()))
            )
            .setCacheWriteDataSinkFactory(null)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }


    private fun createDataSourceFactory(context: Context): DataSource.Factory {
        return ResolvingDataSource.Factory(createCacheDataSource(context)) { dataSpec ->
            val mediaId = dataSpec.key ?: error("No media id")
            val playerResponse = runBlocking(Dispatchers.IO) {
                YouTube.player(mediaId)
            }.getOrElse { throwable ->
                when (throwable) {
                    is ConnectException, is UnknownHostException -> {
                        throw PlaybackException("", throwable, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED)
                    }

                    is SocketTimeoutException -> {
                        throw PlaybackException("", throwable, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT)
                    }

                    else -> throw PlaybackException("", throwable, PlaybackException.ERROR_CODE_REMOTE_ERROR)
                }
            }

            val format = playerResponse.streamingData?.adaptiveFormats
                ?.filter { it.isAudio }
                ?.maxByOrNull {
                    it.bitrate * 1 + (if (it.mimeType.startsWith("audio/webm")) 10240 else 0) // prefer opus stream
                }
            dataSpec.withUri(format!!.url!!.toUri()).subrange(dataSpec.uriPositionOffset, CHUNK_LENGTH)
        }
    }
}