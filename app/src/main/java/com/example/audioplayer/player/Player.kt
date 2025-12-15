package com.example.audioplayer.player

import androidx.media3.common.Player as Media3Player
import androidx.media3.exoplayer.ExoPlayer

class Player(private val player: ExoPlayer) {

    val isPlaying: Boolean get() = player.isPlaying
    val currentPositionMs: Long get() = player.currentPosition
    val durationMs: Long get() = player.duration.coerceAtLeast(0L)
    val bufferedPositionMs: Long get() = player.bufferedPosition


    fun play() = player.play()
    fun pause() = player.pause()
    fun seekTo(positionMs: Long) = player.seekTo(positionMs)

    fun addListener(listener: Media3Player.Listener) = player.addListener(listener)
    fun removeListener(listener: Media3Player.Listener) = player.removeListener(listener)

    fun release() = player.release()
}
