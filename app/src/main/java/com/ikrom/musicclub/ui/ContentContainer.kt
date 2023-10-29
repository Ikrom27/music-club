package com.ikrom.musicclub.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ikrom.musicclub.ui.screens.ExploreScreen
import com.ikrom.musicclub.ui.screens.HomeScreen
import com.ikrom.musicclub.ui.screens.LibraryScreen
import com.ikrom.musicclub.view_model.HomeViewModel


@Composable
fun ContentContainer(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "home")
    {
        composable("home"){
            HomeScreen(
                homeViewModel
            )
        }
        composable("explore"){
            ExploreScreen()
        }
        composable("library"){
            LibraryScreen()
        }
    }
}