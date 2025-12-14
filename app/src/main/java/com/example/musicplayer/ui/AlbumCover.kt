package com.example.musicplayer.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun AlbumCover(imageResult: ImageResult) {
    when (imageResult) {
        ImageResult.Error -> Box(
            modifier = Modifier
                .size(88.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.Audiotrack,
                contentDescription = "Album placeholder",
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(48.dp),
            )
        }
        is ImageResult.Success -> Image(
            painter = painterResource(imageResult.drawableRes),
            contentDescription = "Album art",
            modifier = Modifier.size(88.dp)
        )
    }
}

sealed interface ImageResult {
    data object Error : ImageResult
    data class Success(@get:DrawableRes val drawableRes: Int) : ImageResult
}