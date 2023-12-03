package com.ikrom.musicclub.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ikrom.musicclub.R

sealed class Screens (
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val route: String
) {
    object Home : Screens(R.string.screen_home, R.drawable.ic_home, "home")
    object Explore : Screens(R.string.screen_explore, R.drawable.ic_explore, "explore")
    object Library : Screens(R.string.screen_library, R.drawable.ic_library, "library")
    object Profile : Screens(R.string.screen_profile, R.drawable.ic_profile, "profile")
}