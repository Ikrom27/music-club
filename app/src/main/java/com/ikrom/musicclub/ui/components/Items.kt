package com.ikrom.musicclub.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.ui.theme.ALBUM_LARGE_COVER_SIZE


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