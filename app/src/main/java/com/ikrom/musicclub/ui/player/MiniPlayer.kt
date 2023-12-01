package com.ikrom.musicclub.ui.player

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.media3.common.C
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ikrom.musicclub.R
import com.ikrom.musicclub.extensions.togglePlayPause
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.ui.theme.MINI_PLAYER_HEIGHT
import com.ikrom.musicclub.view_model.PlayerViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MiniPlayer(
    playerViewModel: PlayerViewModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTrack by remember { playerViewModel.getCurrentMediaItem() }
    val isPlaying by playerViewModel.isPlaying.collectAsState()

    Box(
        modifier = modifier
            .height(MINI_PLAYER_HEIGHT)
            .fillMaxWidth()
            .clickable { onClick() },
        contentAlignment = Alignment.TopStart
    ){
        Row(
            modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = if (currentTrack == null) R.drawable.ic_track_cover else currentTrack!!.mediaMetadata.artworkUri,
                contentDescription = "---",
                modifier = Modifier
                    .width(MINI_PLAYER_HEIGHT - 8.dp)
                    .clip(MaterialTheme.shapes.small),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentTrack?.mediaMetadata?.title.toString()
                )
                Text(
                    text = currentTrack?.mediaMetadata?.artist.toString(),
                    color = Color.Gray
                )
            }
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(R.drawable.ic_favorite_border),
                    contentDescription = "",
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(
                onClick = { playerViewModel.player.togglePlayPause() },
            ) {
                Icon(
                    painter = painterResource(if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                    contentDescription = "Next",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}