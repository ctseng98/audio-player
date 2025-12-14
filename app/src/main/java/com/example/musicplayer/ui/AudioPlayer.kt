package com.example.musicplayer.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicplayer.R
import com.example.musicplayer.player.Player
import com.example.musicplayer.ui.theme.PrimaryBackground
import com.example.musicplayer.ui.theme.PrimaryBar
import com.example.musicplayer.ui.theme.Typography
import kotlinx.coroutines.time.delay
import java.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import androidx.core.net.toUri

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AlbumCover()
            Spacer(Modifier.size(16.dp))
            Column {
                Text(
                    text = "Black Friday (pretty like the sun)",
                    color = Color.White,
                    style = Typography.headlineMedium,
                    overflow = TextOverflow.Visible,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee(
                        velocity = 20.dp,          // slower than default
                        initialDelayMillis = 800,  // wait a bit before starting
                        repeatDelayMillis = 1500,        // pause at the end before looping
                    )
                )
                Spacer(Modifier.size(16.dp))
                Text(
                    text = "Lost Frequencies, Tom Odell, Poppy Baskcomb",
                    color = Color.White,
                    style = Typography.bodyMedium,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.basicMarquee(
                        velocity = 20.dp,
                        initialDelayMillis = 800,
                        repeatDelayMillis = 1500,
                    )
                )
            }
        }
        AudioPlayerControls(player = player)
    }
}

@Composable
private fun AlbumCover() {
    Image(
        painter = painterResource(R.drawable.album),
        contentDescription = "Album art",
        modifier = Modifier.size(88.dp)
    )
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
