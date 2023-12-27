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
        .maximumColorCount(8)
        .generate()
        .swatches
        .associate { it.rgb to it.population }

    // Сортируем цвета по убыванию яркости (от темного к светлому)
    val rankedColors = Score.score(colorsToPopulation).sortedByDescending { it.luminance }

    // Выбираем первый (самый темный) цвет
    val darkColor = Color(rankedColors.first())

    // Понижаем яркость цвета вдвое
    val finalColor = ColorUtils.blendARGB(Color.Black.toArgb(), darkColor.toArgb(), 0.3f)

    return Color(finalColor)
}