package com.example.musicplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicplayer.R
import com.example.musicplayer.player.Player
import com.example.musicplayer.ui.theme.PrimaryBackground
import com.example.musicplayer.ui.theme.Typography

@Composable
fun AudioPlayer(
    modifier: Modifier = Modifier,
) {
    val player = rememberPlayer()
    AudioContent(player, modifier)
}

@Composable
private fun AudioContent(player: Player, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(480.5.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(PrimaryBackground),
    ) {
        val marqueeModifier = Modifier.basicMarquee(
            velocity = 20.dp,
            initialDelayMillis = 800,
            repeatDelayMillis = 1500,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AlbumCover(ImageResult.Success(R.drawable.album))
            Spacer(Modifier.size(16.dp))
            Column {
                Text(
                    text = "Black Friday (pretty like the sun)",
                    color = Color.White,
                    style = Typography.headlineMedium,
                    overflow = TextOverflow.Visible,
                    maxLines = 1,
                    modifier = marqueeModifier,
                )
                Spacer(Modifier.size(16.dp))
                Text(
                    text = "Lost Frequencies, Tom Odell, Poppy Baskcomb",
                    color = Color.White.copy(alpha = 0.5f),
                    style = Typography.bodyMedium,
                    overflow = TextOverflow.Visible,
                    modifier = marqueeModifier,
                )
            }
        }
        AudioPlayerControls(player = player)
    }
}

@Composable
private fun rememberPlayer(): Player {
    val context = LocalContext.current
    val uri = "android.resource://${context.packageName}/raw/no_copyright_music".toUri()
    val player = remember {
        val exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
        }
        Player(player = exoPlayer)
    }
    DisposableEffect(Unit) {
        onDispose { player.release() }
    }
    return player
}

@Composable
@Preview
private fun AudioPlayerPreview() {
    AudioPlayer()
}
