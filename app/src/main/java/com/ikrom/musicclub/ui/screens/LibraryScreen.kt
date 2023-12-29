package com.ikrom.musicclub.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ikrom.musicclub.ui.components.TrackColumnItem
import com.ikrom.musicclub.view_model.LibraryViewModel
import com.ikrom.musicclub.view_model.PlayerViewModel


@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel,
    playerViewModel: PlayerViewModel
){
    libraryViewModel.getFavoriteTracks()

    val trackList by libraryViewModel.favoriteTracks.collectAsState()

    LazyColumn{
        itemsIndexed(items = trackList){index, it ->
            TrackColumnItem(
                track = it,
                index = index,
                onItemClick = {
                    playerViewModel.playNow(it)
                },
                onButtonClick = { /*TODO*/ })
        }
    }
}