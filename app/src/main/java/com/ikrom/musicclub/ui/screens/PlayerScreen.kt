package com.ikrom.musicclub.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.SeekBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.R
import com.ikrom.musicclub.extensions.togglePlayPause
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.view_model.PlayerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.seconds

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerScreen(
    navController: NavHostController,
    playerViewModel: PlayerViewModel
){
    val currentTrack by playerViewModel.getCurrentMediaItem().collectAsState()
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
        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(painter = painterResource(R.drawable.ic_array_down), contentDescription = "")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { }) {
                Icon(painter = painterResource(R.drawable.ic_more_horizontal), contentDescription = "")
            }
        }

        GlideImage(
            model = currentTrack!!.mediaMetadata.artworkUri,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(
                    text = currentTrack!!.mediaMetadata.title.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp
                )
                Text(
                    text = currentTrack!!.mediaMetadata.artist.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                IconButton(onClick = {  }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_favorite_border),
                        contentDescription = "",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }
        Text(
            text = makeTimeString(sliderPosition ?: currentPosition),
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Slider(
            value = (sliderPosition ?: currentPosition).toFloat(),
            valueRange = 0f..(if (totalDuration == C.TIME_UNSET) 0f else totalDuration.toFloat()),
            onValueChange = {
                sliderPosition = it.toLong()
            },
            onValueChangeFinished = {
                sliderPosition?.let {
                    playerViewModel.player.seekTo(it)
                    currentPosition = it
                }
                sliderPosition = null
            }
        )

        TrackControls(
            modifier = Modifier.padding(top = 24.dp),
            isPlaying = isPlaying,
            onPlayPause = {playerViewModel.player.togglePlayPause()},
            onNextClick = {playerViewModel.player.seekToNext()},
            onPreviousClick = {playerViewModel.player.seekToPrevious()}
        )
    }
}

@Composable
fun TrackControls(
    modifier: Modifier,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
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

        IconButton(
            onClick = { onPlayPause() },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                contentDescription = if (isPlaying) "Pause" else "Play",
                modifier = Modifier.size(70.dp)
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
    }
}

fun makeTimeString(duration: Long?): String {
    if (duration == null || duration < 0) return ""
    var sec = duration / 1000
    val day = sec / 86400
    sec %= 86400
    val hour = sec / 3600
    sec %= 3600
    val minute = sec / 60
    sec %= 60
    return when {
        day > 0 -> "%d:%02d:%02d:%02d".format(day, hour, minute, sec)
        hour > 0 -> "%d:%02d:%02d".format(hour, minute, sec)
        else -> "%d:%02d".format(minute, sec)
    }
}