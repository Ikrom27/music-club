package com.ikrom.musicclub.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
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
import com.ikrom.musicclub.ui.components.ApplicationTopBar
import com.ikrom.musicclub.ui.components.NewReleaseRow
import com.ikrom.musicclub.ui.components.TrackRow
import com.ikrom.musicclub.ui.theme.mainHorizontalPadding
import com.ikrom.musicclub.view_model.HomeViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    val trackList by homeViewModel.getTracksByQuery("Linkin park").collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val albumList by homeViewModel.getNewRelease().collectAsState()

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
                    text = "From Linkin Park",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(mainHorizontalPadding, 16.dp)
                )
                LazyRow {
                    items(items = trackList){track->
                        TrackRow(
                            title = track.title,
                            cover = track.album.cover,
                            onItemClick = { /*TODO*/},
                            onLongClick = { /*TODO*/ }
                        )
                    }
                }
            }
            item {
                Text(
                    text = "New Release",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(mainHorizontalPadding, 16.dp)
                )
                LazyRow {
                    items(items = albumList){album->
                        NewReleaseRow(
                            title = album.title,
                            cover = album.cover,
                            onItemClick = { /*TODO*/},
                            onLongClick = { /*TODO*/ }
                        )
                    }
                }
            }
        }
    }
}