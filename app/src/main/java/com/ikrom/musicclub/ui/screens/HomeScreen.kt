package com.ikrom.musicclub.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ikrom.musicclub.extensions.getNames
import com.ikrom.musicclub.ui.components.ApplicationTopBar
import com.ikrom.musicclub.ui.components.NewReleaseRow
import com.ikrom.musicclub.ui.components.TrackRow
import com.ikrom.musicclub.ui.theme.BETWEEN_ROW_ITEMS_SPACE
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.view_model.AlbumViewModel
import com.ikrom.musicclub.view_model.HomeViewModel
import com.ikrom.musicclub.playback.PlayerConnection


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    playerConnection: PlayerConnection,
    albumViewModel: AlbumViewModel
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val trackList by homeViewModel.queryList.collectAsState()
    val albumList by homeViewModel.releaseList.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ApplicationTopBar(scrollBehavior)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = it.calculateTopPadding())
        ) {
            item {
                Text(
                    text = "Лучшее от Linkin Park",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(MAIN_HORIZONTAL_PADDING, 16.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(start = MAIN_HORIZONTAL_PADDING, end = MAIN_HORIZONTAL_PADDING),
                    horizontalArrangement = Arrangement.spacedBy(BETWEEN_ROW_ITEMS_SPACE)
                ) {
                    items(items = trackList){track->
                        TrackRow(
                            title = track.title,
                            author = track.album.artists.getNames(),
                            cover = track.album.cover,
                            onItemClick = {
                                playerConnection.playNow(track)
                            },
                            onLongClick = { /*TODO*/ }
                        )
                    }
                }
                Text(
                    text = "Новые релизы",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(MAIN_HORIZONTAL_PADDING, 16.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(start = MAIN_HORIZONTAL_PADDING, end = MAIN_HORIZONTAL_PADDING),
                    horizontalArrangement = Arrangement.spacedBy(BETWEEN_ROW_ITEMS_SPACE)
                ) {
                    items(items = albumList){album->
                        NewReleaseRow(
                            title = album.title,
                            author = album.artists.getNames(),
                            cover = album.cover,
                            onItemClick = {
                                albumViewModel.currentAlbum = album
                                navController.navigate("album")
                            },
                            onLongClick = { /*TODO*/ }
                        )
                    }
                }
            }
        }
    }
}