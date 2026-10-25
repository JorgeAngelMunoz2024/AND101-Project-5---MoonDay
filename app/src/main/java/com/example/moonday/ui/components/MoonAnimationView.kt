package com.example.moonday.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private val MOON_PATHS = listOf(
    // 0: Waxing Crescent
    "M 110.0000 8.8984 C 163.9609 8.8984 207.7578 54.1484 207.7578 109.9766 C 207.7578 136.7969 197.4375 162.5273 179.0977 181.5000 C 160.7617 200.4727 135.9297 211.1016 110.0000 211.1016 C 99.5859 211.0547 89.2734 209.2656 79.3867 205.8359 C 119.4062 192.2266 146.4844 153.5859 146.5312 109.9766 C 146.5312 66.3164 119.2617 27.6211 79.1484 14.0625 C 89.0820 10.6367 99.4922 8.8984 110.0000 8.8984 Z",
    // 1: First Quarter
    "M 110.0000 8.8984 C 135.9297 8.8984 160.8086 19.5273 179.0977 38.5000 C 197.4375 57.4727 207.7500 83.2031 207.7500 109.9766 C 207.7500 165.8516 164.0078 211.1016 110.0000 211.1016 Z",
    // 2: Waxing Gibbous
    "M 110.0000 8.8984 C 163.9609 8.8984 207.7500 54.1484 207.7500 109.9766 C 207.7500 136.7969 197.4414 162.5273 179.1016 181.5000 C 160.7695 200.4727 135.9297 211.1016 110.0000 211.1016 C 92.8125 211.0547 75.9492 206.3359 61.1016 197.3438 C 91.2812 179.3633 109.9102 146.0352 110.0000 109.9766 C 109.8711 73.9141 91.2305 40.5859 60.9609 22.5586 C 75.8594 13.6172 92.7617 8.8984 110.0000 8.8984 Z",
    // 3: Full Moon
    "M 211.0977 109.9766 C 211.0977 165.8516 165.8086 211.1016 110.0000 211.1016 C 83.1797 211.1016 57.4492 200.4727 38.4805 181.5000 C 19.5195 162.5273 8.8906 136.7969 8.8906 109.9766 C 8.8906 83.2031 19.5195 57.4727 38.4805 38.5000 C 57.4492 19.5273 83.1797 8.8984 110.0000 8.8984 C 165.8086 8.8984 211.0977 54.1484 211.0977 109.9766 Z",
    // 4: Waning Gibbous
    "M 110.0000 8.8984 C 56.0000 8.8984 12.2500 54.1484 12.2500 109.9766 C 12.2500 136.7969 22.5195 162.5273 40.8594 181.5000 C 59.1992 200.4727 84.0781 211.1016 110.0000 211.1016 C 127.1992 211.0547 144.0586 206.3359 158.8594 197.3438 C 128.6797 179.3633 110.0508 146.0352 110.0000 109.9766 C 110.0000 73.9141 128.7695 40.5859 159.0000 22.5586 C 144.1484 13.6172 127.1992 8.8984 110.0000 8.8984 Z",
    // 5: Last Quarter
    "M 110.0000 8.8984 C 84.0703 8.8984 59.1914 19.5273 40.8516 38.5000 C 22.5117 57.4727 12.2500 83.2031 12.2500 109.9766 C 12.2500 165.8516 56.0000 211.1016 110.0000 211.1016 Z",
    // 6: Waning Crescent
    "M 110.0000 8.8984 C 56.0391 8.8984 12.2500 54.1484 12.2500 109.9766 C 12.2500 136.7969 22.5703 162.5273 40.9102 181.5000 C 59.2500 200.4727 84.0781 211.1016 110.0000 211.1016 C 120.4141 211.0547 130.7266 209.2656 140.6133 205.8359 C 100.5938 192.2266 73.5156 153.5859 73.4688 109.9766 C 73.4688 66.3164 100.7383 27.6211 140.8516 14.0625 C 130.9180 10.6367 120.5078 8.8984 110.0000 8.8984 Z"
)

@Composable
fun MoonAnimationView(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp
) {
    var currentFrameIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(600)
            currentFrameIndex = (currentFrameIndex + 1) % MOON_PATHS.size
        }
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = currentFrameIndex,
            animationSpec = tween(durationMillis = 350),
            label = "MoonPhaseCrossfade"
        ) { index ->
            val pathData = MOON_PATHS[index]
            val parsedPath = remember(pathData) {
                PathParser().parsePathString(pathData).toPath()
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                val scaleX = size.toPx() / 220f
                val scaleY = size.toPx() / 220f

                drawContext.canvas.save()
                drawContext.canvas.scale(scaleX, scaleY)
                drawPath(
                    path = parsedPath,
                    color = Color.White
                )
                drawContext.canvas.restore()
            }
        }
    }
}
