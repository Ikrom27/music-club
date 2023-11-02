package com.ikrom.musicclub.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ikrom.musicclub.ui.components.ExploreBar


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExploreScreen(){
    Scaffold(
        topBar = {
            ExploreBar(onSearchClick = {})
        }
    ) {

    }
}