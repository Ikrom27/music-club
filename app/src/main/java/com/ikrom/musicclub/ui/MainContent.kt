package com.ikrom.musicclub.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ikrom.musicclub.ui.components.BottomNavBar
import com.ikrom.musicclub.ui.components.Screens


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navigationItems = remember {
        listOf(Screens.Home, Screens.Explore, Screens.Library)
    }

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            BottomNavBar(navController, navBackStackEntry, navigationItems)
        }
    ){
        ContentContainer(
            navController = navController
        )
    }
}