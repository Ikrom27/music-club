package com.ikrom.musicclub.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ikrom.musicclub.ui.components.TrackColumnItem
import com.ikrom.musicclub.view_model.LibraryViewModel
import com.ikrom.musicclub.playback.PlayerConnection


@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel,
    playerConnection: PlayerConnection
){
    libraryViewModel.getFavoriteTracks()

    val trackList by libraryViewModel.favoriteTracks.collectAsState()

    LazyColumn{
        itemsIndexed(items = trackList){index, it ->
            TrackColumnItem(
                track = it,
                index = index,
                onItemClick = {
                    playerConnection.playNow(it)
                },
                onButtonClick = { /*TODO*/ })
        }
    }
}