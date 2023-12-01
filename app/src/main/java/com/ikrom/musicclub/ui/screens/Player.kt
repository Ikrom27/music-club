package com.ikrom.musicclub.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ikrom.musicclub.ui.components.BottomSheet
import com.ikrom.musicclub.ui.components.BottomSheetState
import com.ikrom.musicclub.view_model.PlayerViewModel


@Composable
fun Player(
    state: BottomSheetState,
    playerViewModel: PlayerViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    BottomSheet(
        state = state,
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(NavigationBarDefaults.Elevation),
        onDismiss = {
            playerViewModel.player.stop()
            playerViewModel.player.clearMediaItems()
        },
        collapsedContent = {
            Box(modifier = Modifier.height(50.dp).fillMaxWidth()){
                Text(text = "Playing")
            }
        }
    ) {
        PlayerScreen(navController = navController, playerViewModel = playerViewModel)
    }
}