package com.ikrom.musicclub.ui.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.luminance
import androidx.palette.graphics.Palette
import com.google.android.material.color.utilities.Score

@SuppressLint("RestrictedApi")
fun Bitmap.extractThemeColor(): Color {
    val colorsToPopulation = Palette.from(this)
        .maximumColorCount(24)
        .generate()
        .swatches
        .associate { it.rgb to it.population }

    var color = Score.score(colorsToPopulation).first()
    if (color.luminance > 0.2){
        color = ColorUtils.blendARGB(
            Color.Black.toArgb(),
            color, 0.5f)
    }
    return Color(color)
}