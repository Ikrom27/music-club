package com.ikrom.musicclub.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ikrom.musicclub.ui.theme.MINI_PLAYER_HEIGHT
import com.ikrom.musicclub.ui.theme.NAVBAR_HEIGHT
import com.ikrom.musicclub.playback.PlayerConnection
import com.ikrom.musicclub.ui.screens.MiniPlayer
import kotlin.math.roundToInt


@Composable
@UnstableApi
fun BottomBar(
    navController: NavController,
    playerConnection: PlayerConnection,
    navBackStackEntry: NavBackStackEntry?,
    navigationItems: List<Screens>,
    onPlayerClick: () -> Unit
){
    val showBottomBar = navBackStackEntry?.destination?.route != "player"
    val showMiniBar by playerConnection.currentTrack.collectAsState()

    val verticalOffset by animateFloatAsState(
        targetValue = if (showBottomBar) 0f else with(LocalDensity.current) {
            (NAVBAR_HEIGHT).toPx() + (MINI_PLAYER_HEIGHT).toPx()
        },
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = ""
    )
    val miniBarVerticalOffset by animateFloatAsState(
        targetValue = if (showMiniBar != null) 0f else with(LocalDensity.current) {
            (MINI_PLAYER_HEIGHT).toPx()
        },
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = ""
    )
    Column(modifier = Modifier.offset { IntOffset(x = 0, y = verticalOffset.roundToInt()) }) {
        MiniPlayer(
            playerConnection = playerConnection,
            onClick = { onPlayerClick() },
            modifier = Modifier
                .offset { IntOffset(x = 0, y = miniBarVerticalOffset.roundToInt()) }
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
        )
        BottomAppBar{
            navigationItems.forEach { screen ->
                val isSelected = navBackStackEntry?.destination?.hierarchy?.any { it.route == screen.route } == true
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { navController.navigate(screen.route)},
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.iconId),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = screen.titleId),
                        )
                    }
                )
            }
        }
    }
}