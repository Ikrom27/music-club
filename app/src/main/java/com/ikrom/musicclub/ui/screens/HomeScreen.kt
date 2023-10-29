package com.ikrom.musicclub.ui.screens

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ikrom.musicclub.ui.components.TrackRow
import com.ikrom.musicclub.view_model.HomeViewModel


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    val trackList by homeViewModel.getTracksByQuery("Linkin park").collectAsState()
    LazyRow {
        items(items = trackList){
            TrackRow(
                title = it.title,
                cover = it.album.cover,
                onItemClick = { /*TODO*/ }) {
            }
        }
    }

}