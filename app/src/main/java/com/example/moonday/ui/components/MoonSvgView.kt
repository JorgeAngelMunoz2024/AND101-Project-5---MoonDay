package com.example.moonday.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun MoonSvgView(
    svgContent: String?,
    modifier: Modifier = Modifier,
    height: Dp = 220.dp
) {
    if (svgContent.isNullOrEmpty()) return

    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(svgContent.toByteArray(Charsets.UTF_8))
        .decoderFactory(SvgDecoder.Factory())
        .crossfade(true)
        .build()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Moon Phase SVG",
            modifier = Modifier.height(height)
        )
    }
}
