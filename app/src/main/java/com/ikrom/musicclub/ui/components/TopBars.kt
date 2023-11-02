package com.ikrom.musicclub.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.SearchBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreBar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onSearchClick: (String) -> Unit
) {
    var userInput by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    SearchBar(
        query = userInput,
        onQueryChange = {
            userInput = it
        },
        onSearch = {
            isActive = false
            onSearchClick(it)
        },
        active = isActive,
        onActiveChange = {
            isActive = it
        },
        modifier = Modifier.fillMaxWidth(),
    ) {}
}