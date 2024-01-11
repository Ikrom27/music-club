package com.ikrom.musicclub.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ikrom.musicclub.ui.screens.AlbumScreen
import com.ikrom.musicclub.ui.screens.ExploreScreen
import com.ikrom.musicclub.ui.screens.HomeScreen
import com.ikrom.musicclub.ui.screens.LibraryScreen
import com.ikrom.musicclub.ui.screens.ProfileScreen
import com.ikrom.musicclub.view_model.AlbumViewModel
import com.ikrom.musicclub.view_model.ExploreViewModel
import com.ikrom.musicclub.view_model.HomeViewModel
import com.ikrom.musicclub.view_model.LibraryViewModel
import com.ikrom.musicclub.playback.PlayerConnection


@Composable
fun ContentContainer(
    homeViewModel: HomeViewModel = hiltViewModel(),
    exploreViewModel: ExploreViewModel = hiltViewModel(),
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    albumViewModel: AlbumViewModel = hiltViewModel(),
    playerConnection: PlayerConnection,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "home")
    {
        composable("home"){
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                playerConnection = playerConnection,
                albumViewModel = albumViewModel
            )
        }
        composable("explore"){
            ExploreScreen(exploreViewModel = exploreViewModel, playerConnection)
        }
        composable("library"){
            LibraryScreen(libraryViewModel, playerConnection)
        }
        composable("profile"){
            ProfileScreen()
        }
        composable("album"){
            AlbumScreen(
                navController = navController,
                playerConnection = playerConnection,
                albumViewModel = albumViewModel
            )
        }
    }
}