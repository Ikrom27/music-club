package com.ikrom.musicclub.ui.components

import android.view.RoundedCorner
import android.widget.GridLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.R
import com.ikrom.musicclub.data.model.Track
import com.ikrom.musicclub.extensions.getNames
import com.ikrom.musicclub.ui.theme.ALBUM_LARGE_COVER_SIZE
import com.ikrom.musicclub.ui.theme.MAIN_HORIZONTAL_PADDING


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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrackColumnItem(
    track: Track,
    onItemClick: () -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .height(68.dp)
            .padding(horizontal = MAIN_HORIZONTAL_PADDING, vertical = 10.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            GlideImage(
                model = track.album.cover,
                contentDescription = "",
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
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(R.drawable.ic_more_horizontal),
                    contentDescription = "",
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
                .padding(top = 4.dp, start = MAIN_HORIZONTAL_PADDING)
        )
    }
}