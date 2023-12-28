package com.ikrom.musicclub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    albumViewModel.loadTracks()
    val albumTracks by albumViewModel.albumTracks.collectAsState()
    if (album != null){
        LazyColumn{
            item {
                AlbumHeader(album, playerViewModel)
            }
            items(items = albumTracks) {
                TrackColumnItem(
                    track = it,
                    onItemClick = { /*TODO*/ },
                    onButtonClick = { /*TODO*/ })
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AlbumHeader(
    album: Album,
    playerViewModel: PlayerViewModel
){
    Column(
        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING)
    ) {
        GlideImage(
            model = album.cover,
            contentDescription = null,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .padding(horizontal = MAIN_HORIZONTAL_PADDING, vertical = 24.dp)
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
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(0.1f)
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_shuffle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}