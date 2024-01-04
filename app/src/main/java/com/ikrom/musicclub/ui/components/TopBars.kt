package com.ikrom.musicclub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ikrom.musicclub.R
import com.ikrom.musicclub.data.model.SearchHistory
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreBar(
    userInput: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onButtonClick: (SearchHistory) -> Unit,
    onItemClick: (SearchHistory) -> Unit,
    searchHistoryList: List<SearchHistory>
) {
    var isActive by remember { mutableStateOf(false) }
    SearchBar(
        query = userInput,
        onQueryChange = {
            onQueryChange(it)
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
    ) {
        LazyColumn {
            items(items = searchHistoryList){
                Row(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .clickable {
                            onItemClick(it)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HistoryText(
                        it.query,
                        onButtonClick = {
                            onButtonClick(it)
                        },
                        modifier = Modifier.padding(horizontal = MAIN_HORIZONTAL_PADDING / 2 )
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Box (
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_vinyl),
                        modifier = Modifier.size(28.dp),
                        tint = Color.Unspecified,
                        contentDescription = "")
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp, 0.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_devices),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp))
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.mediumTopAppBarColors()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopBar(
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
){
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .padding(horizontal = MAIN_HORIZONTAL_PADDING, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(modifier = Modifier.clickable {
                    onBackClick()
                }){
                    Icon(
                        painter = painterResource(R.drawable.ic_array_back),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable {
                            onFavoriteClick()
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_favorite_border),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(4.dp)
                            .padding(top = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable {
                            onMenuClick()
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_horizontal),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )

}