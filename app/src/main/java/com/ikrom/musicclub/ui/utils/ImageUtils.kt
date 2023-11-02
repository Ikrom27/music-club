package com.ikrom.musicclub.ui.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color


fun getDominantColor(bitmap: Bitmap): Color {
    val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
    val color = newBitmap.getPixel(0, 0)
    newBitmap.recycle()
    return Color(color)
}