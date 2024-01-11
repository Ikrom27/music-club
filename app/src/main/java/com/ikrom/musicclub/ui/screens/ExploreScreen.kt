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
import com.ikrom.musicclub.ui.components.ExploreBar
import com.ikrom.musicclub.ui.components.TrackColumnItem
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.ui.theme.MINI_PLAYER_HEIGHT
import com.ikrom.musicclub.ui.theme.NAVBAR_HEIGHT
import com.ikrom.musicclub.view_model.ExploreViewModel
import com.ikrom.musicclub.playback.PlayerConnection


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel,
    playerConnection: PlayerConnection
){
    var userInput by remember { mutableStateOf( exploreViewModel.userInput) }
    val requestList by exploreViewModel.requestList.collectAsState()
    val searchHistory by exploreViewModel.searchHistory.collectAsState()

    Scaffold(
        topBar = {
            ExploreBar(
                userInput = userInput,
                onQueryChange = {
                    userInput = it
                    exploreViewModel.updateSearchHistory(it)
                },
                onSearchClick = {
                    exploreViewModel.userInput = it
                    exploreViewModel.search()
                },
                searchHistoryList = searchHistory,
                onButtonClick = {
                    exploreViewModel.deleteSearchHistory(it.query)
                },
                onItemClick = {
                    userInput = it.query
                },
                onClearClick = {
                    userInput = ""
                    exploreViewModel.search("")
                }
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
            items(items = requestList){track ->
                TrackColumnItem(
                    track = track,
                    onItemClick = {
                        playerConnection.playNow(track)
                    },
                    onButtonClick = {},
                    modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING)
                )
            }
        }
    }
}