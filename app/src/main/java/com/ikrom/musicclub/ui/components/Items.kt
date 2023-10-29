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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ikrom.musicclub.ui.theme.MusicClubTheme
import com.ikrom.musicclub.ui.theme.TRACK_ROW_IMAGE


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun TrackRow(
    title: String,
    cover: String,
    onItemClick: () -> Unit,
    onLongClick: () -> Unit,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.combinedClickable(
            onClick = onItemClick,
            onLongClick = onLongClick
        )
    ) {
        GlideImage(
            model = cover,
            contentDescription = null,
            modifier = Modifier
                .size(TRACK_ROW_IMAGE, TRACK_ROW_IMAGE)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 22.sp,
            modifier = Modifier
                .requiredWidthIn(
                    max = 115.dp
                )
                .padding(
                    top = 8.dp
                )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    MusicClubTheme {
        TrackRow(
            "Stressed out",
            "https://upload.wikimedia.org/wikipedia/ru/thumb/9/9d/Blurryface.jpeg/274px-Blurryface.jpeg",
            onItemClick = {},
            onLongClick = {}
        )
    }
}