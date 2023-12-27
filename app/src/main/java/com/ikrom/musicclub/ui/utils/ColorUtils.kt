package com.ikrom.musicclub.ui.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import com.google.android.material.color.utilities.Score

@SuppressLint("RestrictedApi")
fun Bitmap.extractThemeColor(): Color {
    val colorsToPopulation = Palette.from(this)
        .maximumColorCount(24)
        .generate()
        .swatches
        .associate { it.rgb to it.population }
    val rankedColors = Score.score(colorsToPopulation)
    val finalColor = ColorUtils.blendARGB(
        Color.Black.toArgb(),
        Color(rankedColors.first()).toArgb(), 0.3f)
    return Color(finalColor)
}