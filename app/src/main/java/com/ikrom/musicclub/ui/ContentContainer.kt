package com.ikrom.musicclub.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ikrom.musicclub.ui.screens.ExploreScreen
import com.ikrom.musicclub.ui.screens.HomeScreen
import com.ikrom.musicclub.ui.screens.LibraryScreen
import com.ikrom.musicclub.ui.player.PlayerScreen
import com.ikrom.musicclub.view_model.ExploreViewModel
import com.ikrom.musicclub.view_model.HomeViewModel
import com.ikrom.musicclub.view_model.LibraryViewModel
import com.ikrom.musicclub.view_model.PlayerViewModel


@Composable
fun ContentContainer(
    homeViewModel: HomeViewModel = hiltViewModel(),
    exploreViewModel: ExploreViewModel = hiltViewModel(),
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel,
    navController: NavHostController,
) {
    homeViewModel.getTracksByQuery("Linkin park")
    homeViewModel.getNewRelease()
    NavHost(
        navController = navController,
        startDestination = "home")
    {
        composable("home"){
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                playerViewModel = playerViewModel
            )
        }
        composable("explore"){
            ExploreScreen(exploreViewModel = exploreViewModel, playerViewModel)
        }
        composable("library"){
            LibraryScreen(libraryViewModel, playerViewModel)
        }
        composable("player"){
            PlayerScreen(navController = navController, playerViewModel = playerViewModel)
        }
    }
}