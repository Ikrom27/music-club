package com.ikrom.musicclub.ui.screens

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.R
import com.ikrom.musicclub.extensions.togglePlayPause
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.view_model.PlayerViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerScreen(
    navController: NavHostController,
    playerViewModel: PlayerViewModel
){
    val currentTrack by playerViewModel.getCurrentMediaItem().collectAsState()
    val isPlaying by playerViewModel.isPlaying.collectAsState()

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
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
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