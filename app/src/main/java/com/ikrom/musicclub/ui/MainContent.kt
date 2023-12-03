package com.ikrom.musicclub.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ikrom.musicclub.ui.components.BottomBar
import com.ikrom.musicclub.ui.components.BottomSheet
import com.ikrom.musicclub.ui.components.Screens
import com.ikrom.musicclub.ui.components.rememberBottomSheetState
import com.ikrom.musicclub.ui.player.PlayerScreen
import com.ikrom.musicclub.view_model.PlayerViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@UnstableApi
@Composable
fun MainContent(
    playerViewModel: PlayerViewModel = hiltViewModel()
){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navigationItems = remember {
        listOf(Screens.Home, Screens.Explore, Screens.Library, Screens.Profile)
    }
    var showPlayerSheet by remember { mutableStateOf(false) }
    val window = (LocalContext.current as Activity).window

    val surfaceColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp).toArgb()

    BoxWithConstraints{
        val playerSheetState = rememberBottomSheetState(
            dismissedBound = 0.dp,
            expandedBound = 1000.dp,
        )
        Scaffold(
            modifier = Modifier,
            bottomBar = {
                Column {
                    BottomBar(
                        navController,
                        playerViewModel,
                        navBackStackEntry,
                        navigationItems,
                        onPlayerClick = {
                            showPlayerSheet = true
                            playerSheetState.expandSoft()
                        },
                    )
                }
            }
        ){
            ContentContainer(
                navController = navController,
                playerViewModel = playerViewModel,
            )
        }

        if (showPlayerSheet){
            BottomSheet(
                state = playerSheetState,
                collapsedContent = {
                    Text(text = "hello world")
                },
                onDismiss = {
                    showPlayerSheet = false
                    window.navigationBarColor = surfaceColor
                }
            ){
                PlayerScreen(playerViewModel = playerViewModel)
            }
        }
    }
}