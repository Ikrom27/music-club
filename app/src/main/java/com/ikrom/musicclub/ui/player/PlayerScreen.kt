package com.ikrom.musicclub.ui.player

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.R
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.extensions.getNames
import com.ikrom.musicclub.extensions.togglePlayPause
import com.ikrom.musicclub.playback.ExoDownloadService
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.utils.makeTimeString
import com.ikrom.musicclub.view_model.PlayerViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@SuppressLint("UnrememberedMutableState")
@UnstableApi
@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel
){
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current
    val currentTrack by remember { mutableStateOf(playerViewModel.currentTrack) }
    val isPlaying by playerViewModel.isPlaying.collectAsState()
    val playbackState by playerViewModel.playbackState.collectAsState()
    val totalDuration by rememberSaveable(playbackState) {
        playerViewModel.totalDuration
    }
    var currentPosition by rememberSaveable(playbackState) {
        playerViewModel.currentPosition
    }
    var sliderPosition by remember {
        mutableStateOf<Long?>(null)
    }

    if (isPlaying) {
        LaunchedEffect(Unit) {
            while(true) {
                currentPosition = playerViewModel.player.currentPosition
                delay(1.seconds / 15)
            }
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .padding(top = 24.dp)
            .width(70.dp).height(5.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(Color.Gray.copy(alpha = 0.3f))
        )

        TrackInfo(
            track = currentTrack,
            isPlaying = isPlaying,
            onButtonClick = {}
        )

        ProgressBar(
            sliderPosition = sliderPosition,
            currentPosition = currentPosition,
            totalDuration = totalDuration,
            onSeek = { sliderPosition = it.toLong() },
            onSeekChanged = {
                sliderPosition?.let {
                    playerViewModel.player.seekTo(it)
                    currentPosition = it
                }
                sliderPosition = null
            }
        )


        PlayerControlButtons(
            modifier = Modifier.padding(top = 24.dp),
            isPlaying = isPlaying,
            onPlayPause = {
                playerViewModel.player.togglePlayPause()
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                          },
            onNextClick = {playerViewModel.player.seekToNext()},
            toFavoriteClick = { playerViewModel.addToFavorite(context) },
            onPreviousClick = {playerViewModel.player.seekToPrevious()}
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrackInfo(
    track: Track?,
    isPlaying: Boolean,
    onButtonClick: () -> Unit
){
    val coverPadding by remember { mutableStateOf(0f) }
    val coverSizeAnimatable = remember { Animatable(coverPadding) }

    LaunchedEffect(isPlaying) {
        coverSizeAnimatable.animateTo(
            targetValue = if (isPlaying) 0f else 40f,
            animationSpec = tween(150)
        )
    }

    Box(modifier = Modifier
        .padding(top = 24.dp)
        .fillMaxWidth()){
        GlideImage(
            model = track?.album?.cover ?: R.drawable.ic_track_cover,
            contentDescription = null,
            modifier = Modifier
                .padding(coverSizeAnimatable.value.dp)
                .clip(MaterialTheme.shapes.large)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column {
            Text(
                text = track?.title ?: "---",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 24.sp
            )
            Text(
                text = track?.album?.artists?.getNames() ?: "---",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable {
                        onButtonClick()
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_more_horizontal),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ProgressBar(
    sliderPosition: Long?,
    currentPosition: Long,
    totalDuration: Long,
    onSeek: (Float) -> Unit,
    onSeekChanged: () -> Unit
){
    Slider(
        value = (sliderPosition ?: currentPosition).toFloat(),
        valueRange = 0f..(if (totalDuration == C.TIME_UNSET) 0f else totalDuration.toFloat()),
        onValueChange = {
            onSeek(it)
        },
        onValueChangeFinished = { onSeekChanged() },
        modifier = Modifier.padding(top = 24.dp)
    )

    Row{
        Text(
            text = makeTimeString(sliderPosition ?: currentPosition),
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = makeTimeString(totalDuration),
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun PlayerControlButtons(
    modifier: Modifier,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNextClick: () -> Unit,
    toFavoriteClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    val cornerSize by remember { mutableStateOf(100.dp) }
    val cornerSizeAnimatable = remember { Animatable(cornerSize.value) }

    LaunchedEffect(isPlaying) {
        cornerSizeAnimatable.animateTo(
            targetValue = if (isPlaying) 100f else 20f,
            animationSpec = tween(150)
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { toFavoriteClick() }) {
            Icon(
                painter = painterResource(R.drawable.ic_favorite_border),
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
        IconButton(
            onClick = { onPreviousClick() },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_skip_previous),
                contentDescription = "Previous",
                modifier = Modifier.size(70.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(cornerSizeAnimatable.value.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {
                    onPlayPause()
                }
        ) {
            Image(
                painter = painterResource(if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(36.dp)
            )
        }

        IconButton(
            onClick = { onNextClick() },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_skip_next),
                contentDescription = "Next",
                modifier = Modifier.size(70.dp)
            )
        }
        IconButton(onClick = {  }) {
            Icon(
                painter = painterResource(R.drawable.ic_repeat),
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}