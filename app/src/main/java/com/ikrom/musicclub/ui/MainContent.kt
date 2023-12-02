package com.ikrom.musicclub.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ikrom.musicclub.ui.components.BottomNavBar
import com.ikrom.musicclub.ui.components.Screens
import com.ikrom.musicclub.ui.player.MiniPlayer
import com.ikrom.musicclub.view_model.PlayerViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContent(
    playerViewModel: PlayerViewModel = hiltViewModel()
){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navigationItems = remember {
        listOf(Screens.Home, Screens.Explore, Screens.Library)
    }

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            Column {
                BottomNavBar(navController, playerViewModel, navBackStackEntry, navigationItems)
            }
        }
    ){
        ContentContainer(
            navController = navController,
            playerViewModel = playerViewModel
        )
    }
}