package com.ikrom.musicclub.ui.components

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.R
import com.ikrom.musicclub.data.model.Album
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.extensions.getNames
import com.ikrom.musicclub.ui.theme.ALBUM_LARGE_COVER_SIZE
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING
import com.ikrom.musicclub.ui.utils.extractThemeColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun TrackRow(
    title: String,
    author: String,
    cover: String,
    onItemClick: () -> Unit,
    onLongClick: () -> Unit,
){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.combinedClickable(
            onClick = onItemClick,
            onLongClick = onLongClick
        )
    ) {
        GlideImage(
            model = cover,
            contentDescription = null,
            modifier = Modifier
                .size(ALBUM_LARGE_COVER_SIZE, ALBUM_LARGE_COVER_SIZE)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 22.sp,
            modifier = Modifier
                .requiredWidthIn(
                    max = ALBUM_LARGE_COVER_SIZE
                )
                .padding(
                    top = 8.dp
                )
        )
        Text(
            text = author,
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 22.sp,
            modifier = Modifier
                .requiredWidthIn(
                    max = ALBUM_LARGE_COVER_SIZE
                )
                .padding(
                    top = 4.dp
                )
        )
    }
}

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun NewReleaseRow(
    title: String,
    author: String,
    cover: String,
    onItemClick: () -> Unit,
    onLongClick: () -> Unit,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .combinedClickable(
                onClick = onItemClick,
                onLongClick = onLongClick
            )
            .clip(MaterialTheme.shapes.medium)
    ) {
        GlideImage(
            model = cover,
            contentDescription = null,
            modifier = Modifier
                .size(ALBUM_LARGE_COVER_SIZE, ALBUM_LARGE_COVER_SIZE)
        )
        Box{
            GlideImage(
                model = cover,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f), blendMode = BlendMode.Darken),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(ALBUM_LARGE_COVER_SIZE)
                    .heightIn(max = 64.dp)
                    .blur(100.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(ALBUM_LARGE_COVER_SIZE)
            ){
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 22.sp,
                    modifier = Modifier
                        .requiredWidthIn(
                            max = ALBUM_LARGE_COVER_SIZE
                        )
                        .padding(
                            top = 8.dp
                        )
                        .padding(horizontal = 8.dp)
                )
                Text(
                    text = author,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 22.sp,
                    modifier = Modifier
                        .requiredWidthIn(
                            max = ALBUM_LARGE_COVER_SIZE
                        )
                        .padding(top = 4.dp, bottom = 8.dp)
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrackColumnItem(
    track: Track,
    index: Int? = null,
    onItemClick: () -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .clickable {
                onItemClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .height(68.dp)
                .padding(vertical = 8.dp)
        ){
            if (index != null){
                Text(
                    text = (index + 1).toString(),
                    fontSize = 16.sp,
                    modifier = Modifier.width(36.dp)
                )
            }
            GlideImage(
                model = track.album.cover,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.small)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = track.title,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.album.artists.getNames(),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = {
                onButtonClick()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_more_horizontal),
                    contentDescription = "",
                )
            }
        }
    }
}

@Composable
fun HistoryText(
    text: String = "",
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = null,
        )
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        IconButton(
            onClick = { onButtonClick() },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
            )
        }
    }

}