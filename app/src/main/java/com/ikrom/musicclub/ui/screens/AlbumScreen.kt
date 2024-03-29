package com.ikrom.musicclub.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.R
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.extensions.getNames
import com.ikrom.musicclub.ui.components.AlbumTopBar
import com.ikrom.musicclub.ui.components.TrackColumnItem
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.view_model.AlbumViewModel
import com.ikrom.musicclub.playback.PlayerConnection

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumScreen(
    navController: NavController,
    playerConnection: PlayerConnection,
    albumViewModel: AlbumViewModel
){
    val album = albumViewModel.currentAlbum
    val albumTracks by albumViewModel.albumTracks.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AlbumTopBar(
                onMenuClick = { },
                onBackClick = { navController.popBackStack() },
                onFavoriteClick = {},
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        if (album != null){
            LazyColumn(
                modifier = Modifier.padding(top = it.calculateTopPadding())
            ){
                item {
                    AlbumHeader(
                        album = album,
                        onPlayClick = {
                            playerConnection.playNow(albumTracks)
                        },
                        onShuffleClick = {
                            playerConnection.playNow(albumTracks.shuffled())
                        },
                        onBackClick = {

                        },
                        onMenuClick = {

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
                        onItemClick = { playerConnection.playNow(track) },
                        onButtonClick = {},
                        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING))
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AlbumHeader(
    album: Album,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit
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
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = album.artists.getNames(),
            fontSize = 16.sp,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 2.dp)
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