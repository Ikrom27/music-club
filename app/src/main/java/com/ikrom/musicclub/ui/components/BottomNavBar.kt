package com.ikrom.musicclub.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ikrom.musicclub.ui.theme.NAVBAR_HEIGHT
import kotlin.math.roundToInt


@Composable
fun BottomNavBar(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?,
    navigationItems: List<Screens>
){
    val showBottomBar = navBackStackEntry?.destination?.route in navigationItems.map { it.route }
    val verticalOffset by animateFloatAsState(
        targetValue = if (showBottomBar) 0f else with(LocalDensity.current) {
            (NAVBAR_HEIGHT).toPx()
        },
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = ""
    )

    BottomAppBar(
        modifier = Modifier.offset { IntOffset(x = 0, y = verticalOffset.roundToInt()) }
    ) {
        navigationItems.forEach { screen ->
            val isSelected = navBackStackEntry?.destination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigate(screen.route)},
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