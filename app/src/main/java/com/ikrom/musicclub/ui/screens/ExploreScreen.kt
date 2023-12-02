package com.ikrom.musicclub.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ikrom.musicclub.extensions.toMediaItem
import com.ikrom.musicclub.ui.components.ExploreBar
import com.ikrom.musicclub.ui.components.TrackColumnItem
import com.ikrom.musicclub.ui.theme.MINI_PLAYER_HEIGHT
import com.ikrom.musicclub.ui.theme.NAVBAR_HEIGHT
import com.ikrom.musicclub.view_model.ExploreViewModel
import com.ikrom.musicclub.view_model.PlayerViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel,
    playerViewModel: PlayerViewModel
){
    var userInput by remember { mutableStateOf(exploreViewModel.userInput) }
    val requestList by exploreViewModel.requestList.collectAsState()

    Scaffold(
        topBar = {
            ExploreBar(
                userInput = userInput,
                onQueryChange = {userInput = it},
                onSearchClick = {
                    exploreViewModel.userInput = it
                    exploreViewModel.search()
                },
            )
        }
    ) {
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = MINI_PLAYER_HEIGHT + NAVBAR_HEIGHT)
        ){
            items(items = requestList, key = {it.videoId}){
                TrackColumnItem(
                    track = it,
                    onItemClick = {
                        playerViewModel.playNow(it.toMediaItem())
                    },
                    onButtonClick = {}
                )
            }
        }
    }
}