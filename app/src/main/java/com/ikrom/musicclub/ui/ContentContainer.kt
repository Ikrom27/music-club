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
import com.ikrom.musicclub.view_model.PlayerViewModel


@Composable
fun ContentContainer(
    homeViewModel: HomeViewModel = hiltViewModel(),
    exploreViewModel: ExploreViewModel = hiltViewModel(),
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    albumViewModel: AlbumViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel,
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
                playerViewModel = playerViewModel,
                albumViewModel = albumViewModel
            )
        }
        composable("explore"){
            ExploreScreen(exploreViewModel = exploreViewModel, playerViewModel)
        }
        composable("library"){
            LibraryScreen(libraryViewModel, playerViewModel)
        }
        composable("profile"){
            ProfileScreen()
        }
        composable("album"){
            AlbumScreen(playerViewModel = playerViewModel, albumViewModel = albumViewModel)
        }
    }
}