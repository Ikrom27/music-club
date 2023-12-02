package com.ikrom.musicclub.ui.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.R
import com.ikrom.musicclub.extensions.togglePlayPause
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.ui.theme.MINI_PLAYER_HEIGHT
import com.ikrom.musicclub.view_model.PlayerViewModel


@OptIn(ExperimentalGlideComposeApi::class)
@UnstableApi
@Composable
fun MiniPlayer(
    playerViewModel: PlayerViewModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTrack by remember { playerViewModel.getCurrentMediaItem() }
    val isPlaying by playerViewModel.isPlaying.collectAsState()
    val context = LocalContext.current

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
            Box(
                modifier = Modifier
                    .size(MINI_PLAYER_HEIGHT - 8.dp)
            ){
                GlideImage(
                    model = if (currentTrack == null) R.drawable.ic_track_cover else currentTrack!!.mediaMetadata.artworkUri,
                    contentDescription = "---",
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small),
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentTrack?.mediaMetadata?.title.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = currentTrack?.mediaMetadata?.artist.toString(),
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = {
                playerViewModel.addToFavorite(context)
            }) {
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