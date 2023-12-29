package com.ikrom.musicclub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.R
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.extensions.getNames
import com.ikrom.musicclub.extensions.toMediaItem
import com.ikrom.musicclub.ui.components.TrackColumnItem
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.view_model.AlbumViewModel
import com.ikrom.musicclub.view_model.PlayerViewModel

@Composable
fun AlbumScreen(
    playerViewModel: PlayerViewModel,
    albumViewModel: AlbumViewModel
){
    val album = albumViewModel.currentAlbum
    val albumTracks by albumViewModel.albumTracks.collectAsState()
    if (album != null){
        LazyColumn{
            item {
                AlbumHeader(
                    album = album,
                    onPlayClick = {
                        playerViewModel.playNow(albumTracks)
                    },
                    onShuffleClick = {
                        playerViewModel.playNow(albumTracks.shuffled())
                    }
                    )
            }
            item {
                Box(
                    modifier = Modifier
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(start = 36.dp)
                )
            }
            itemsIndexed(items = albumTracks) {index, track ->
                TrackColumnItem(
                    index = index,
                    track = track,
                    onItemClick = { playerViewModel.playNow(track) },
                    onButtonClick = {},
                    modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING))
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AlbumHeader(
    album: Album,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit
){
    Column(
        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = album.cover,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = MAIN_HORIZONTAL_PADDING * 3, vertical = 24.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Text(
            text = album.title,
            fontSize = 16.sp,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = album.artists.getNames(),
            fontSize = 14.sp,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .padding(top = 12.dp)
        ){
            Box(
                modifier = Modifier
                    .width(0.dp)
                    .weight(0.45f)
                    .clip(MaterialTheme.shapes.medium)
                    .height(40.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable {
                        onPlayClick()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 12.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(0.1f)
            )
            Box(
                modifier = Modifier
                    .width(0.dp)
                    .weight(0.45f)
                    .clip(MaterialTheme.shapes.medium)
                    .height(40.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable {
                        onShuffleClick()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_shuffle),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}