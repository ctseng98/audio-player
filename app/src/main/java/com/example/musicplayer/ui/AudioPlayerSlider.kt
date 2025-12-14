package com.example.musicplayer.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.PrimaryBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SliderTrackWithBuffer(
    thumbSize: Dp,
    sliderState: SliderState,
    activeTrackColor: Color,
    inactiveTrackColor: Color,
    secondaryProgressPercentage: Float = 0.0f,
    secondaryProgressColor: Color = PrimaryBar,
) {
    val height = 8.dp
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
    ) {
        val trackStrokeWidth = height.toPx()
        val half = trackStrokeWidth / 2f
        val thumbPadding = (thumbSize / 2).toPx()

        val isRtl = layoutDirection == LayoutDirection.Rtl

        // Inset start/end so round caps don't draw outside the canvas
        val leftX = half
        val rightX = size.width - half

        val sliderLeft = Offset(leftX - thumbPadding, center.y)
        val sliderRight = Offset(rightX + thumbPadding, center.y)

        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight

        // Normalize value -> fraction within range
        val rangeStart = sliderState.valueRange.start
        val rangeEnd = sliderState.valueRange.endInclusive
        val range = (rangeEnd - rangeStart).takeIf { it > 0f } ?: 1f
        val fraction = ((sliderState.value - rangeStart) / range).coerceIn(0f, 1f)

        val sliderValueStart = sliderStart // active track starts at track start
        val sliderValueEnd = Offset(
            x = sliderStart.x + (sliderEnd.x - sliderStart.x) * fraction,
            y = center.y
        )

        val secondaryFraction = secondaryProgressPercentage.coerceIn(0f, 1f)
        val secondaryX = if (isRtl) {
            sliderStart.x + (sliderEnd.x - sliderStart.x) * (1f - secondaryFraction)
        } else {
            sliderStart.x + (sliderEnd.x - sliderStart.x) * secondaryFraction
        }
        val secondaryTrackEnd = Offset(secondaryX, center.y)

        drawLine(
            color = inactiveTrackColor,
            start = sliderStart,
            end = sliderEnd,
            strokeWidth = trackStrokeWidth,
            cap = StrokeCap.Round,
        )

        if ((isRtl && secondaryTrackEnd.x < sliderValueEnd.x) ||
            (!isRtl && secondaryTrackEnd.x > sliderValueEnd.x)
        ) {
            drawLine(
                color = secondaryProgressColor,
                start = sliderValueEnd,
                end = secondaryTrackEnd,
                strokeWidth = trackStrokeWidth,
                cap = StrokeCap.Round,
            )
        }

        drawLine(
            color = activeTrackColor,
            start = sliderValueStart,
            end = sliderValueEnd,
            strokeWidth = trackStrokeWidth,
            cap = StrokeCap.Round,
        )
    }
}