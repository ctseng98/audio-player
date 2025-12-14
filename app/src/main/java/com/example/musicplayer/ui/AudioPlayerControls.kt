package com.example.musicplayer.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.RepeatOn
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.musicplayer.player.Player
import com.example.musicplayer.ui.theme.PrimaryBar
import com.example.musicplayer.ui.theme.SecondaryBar
import com.example.musicplayer.ui.theme.Typography
import kotlinx.coroutines.delay
import androidx.media3.common.Player as Media3Player

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerControls(
    player: Player,
    modifier: Modifier = Modifier,
) {
    var isPlaying by remember { mutableStateOf(player.isPlaying) }
    var durationMs by remember { mutableLongStateOf(player.durationMs) }
    var positionMs by remember { mutableLongStateOf(player.currentPositionMs) }

    var isUserSeeking by remember { mutableStateOf(false) }
    var scrubMs by remember { mutableLongStateOf(0L) }
    val interactionSource = remember { MutableInteractionSource() }

    var bufferedPositionMs by remember { mutableLongStateOf(0L) }

    // Listen to ExoPlayer state changes (play/pause, duration becoming known, etc.)
    DisposableEffect(player) {
        val listener = object : Media3Player.Listener {
            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying = isPlayingNow
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                bufferedPositionMs = player.bufferedPositionMs
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                durationMs = player.durationMs
                positionMs = player.currentPositionMs
                bufferedPositionMs = player.bufferedPositionMs
            }
        }

        player.addListener(listener)
        onDispose { player.removeListener(listener) }
    }

    LaunchedEffect(isPlaying, isUserSeeking) {
        while (isPlaying) {
            if (!isUserSeeking) {
                positionMs = player.currentPositionMs
                durationMs = player.durationMs
            }
            delay(250L)
        }
    }

    val shownPositionMs = if (isUserSeeking) scrubMs else positionMs
    val safeDurationMs = durationMs.coerceAtLeast(0L)
    val thumbSize by animateDpAsState(
        targetValue = if (isUserSeeking) 22.dp else 12.dp,
        label = "thumbSize"
    )

    Column(modifier = modifier.padding(horizontal = 32.dp)) {
        Spacer(Modifier.size(16.dp))
        Slider(
            value = shownPositionMs.toFloat(),
            valueRange = 0f..safeDurationMs.toFloat().coerceAtLeast(0f),
            onValueChange = { v ->
                isUserSeeking = true
                scrubMs = v.toLong()
            },
            thumb = {
                Spacer(
                    Modifier
                        .absolutePadding(top = 2.dp, left = 4.dp)
                        .size(thumbSize)
                        .hoverable(interactionSource = interactionSource)
                        .background(Color.White, CircleShape),
                )
            },
            track = { sliderState ->
                SliderTrackWithBuffer(
                    sliderState = sliderState,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = SecondaryBar,
                    secondaryProgressPercentage = bufferedPositionMs / durationMs.toFloat(),
                    secondaryProgressColor = PrimaryBar,
                    thumbSize = thumbSize,
                )
            },
            onValueChangeFinished = {
                isUserSeeking = false
                player.seekTo(scrubMs.coerceIn(0L, safeDurationMs))
                // snap UI immediately
                positionMs = scrubMs.coerceIn(0L, safeDurationMs)
            },
            enabled = safeDurationMs > 0L,
            modifier = Modifier.fillMaxWidth(),
        )

        DurationIndicator(
            shownPositionMs = shownPositionMs,
            safeDurationMs = safeDurationMs,
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var isOnRepeat by remember { mutableStateOf(false) }
            ButtonWithInteractionFeedback(
                icon = if (isOnRepeat) {
                    Icons.Rounded.RepeatOn
                } else {
                    Icons.Rounded.Repeat
                },
                description = "Repeat",
                onClick = {
                    if (isOnRepeat) {
                        Icons.Rounded.Repeat
                    } else {
                        Icons.Rounded.RepeatOn
                    }
                    isOnRepeat = !isOnRepeat
                },
            )
            ButtonWithInteractionFeedback(
                icon = Icons.Rounded.SkipPrevious,
                description = "Skip to previous track",
                onClick = { },
            )
            PlayButton(
                isPlaying = isPlaying,
                onPause = { player.pause() },
                onPlay = { player.play() }
            )
            ButtonWithInteractionFeedback(
                icon = Icons.Rounded.SkipNext,
                description = "Skip to next track",
                onClick = { },
            )
            var isFavoriteTrack by remember { mutableStateOf(false) }
            ButtonWithInteractionFeedback(
                icon = if (isFavoriteTrack) {
                    Icons.Rounded.Favorite
                } else {
                    Icons.Rounded.FavoriteBorder
                },
                description = "Favorite",
                onClick = {
                    if (isFavoriteTrack) {
                        Icons.Rounded.FavoriteBorder
                    } else {
                        Icons.Rounded.Favorite
                    }
                    isFavoriteTrack = !isFavoriteTrack
                },
            )
        }
        Spacer(Modifier.size(32.dp))
    }
}

@Composable
private fun DurationIndicator(
    shownPositionMs: Long,
    safeDurationMs: Long
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = formatTimeMs(shownPositionMs),
            color = Color.White,
            style = Typography.bodyMedium
        )
        Text(
            text = formatTimeMs(safeDurationMs),
            color = Color.White,
            style = Typography.bodyMedium
        )
    }
}

private fun formatTimeMs(ms: Long): String {
    val clamped = ms.coerceAtLeast(0L)
    val totalSeconds = clamped / 1000L
    val minutes = totalSeconds / 60L
    val seconds = totalSeconds % 60L
    return "%d:%02d".format(minutes, seconds)
}
