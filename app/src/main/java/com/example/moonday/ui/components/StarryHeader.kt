package com.example.moonday.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moonday.BubblyFontFamily
import kotlin.random.Random

@Composable
fun StarryHeader(
    title: String,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val stars = remember {
        val random = Random(42)
        List(45) {
            Triple(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat() * 2.5f + 1f
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2C1E62),
                        Color(0xFF131428),
                        Color(0xFF0D0E15)
                    )
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 14.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            stars.forEach { (xPct, yPct, radius) ->
                drawCircle(
                    color = Color.White.copy(alpha = (0.3f + (xPct * 0.7f)).coerceIn(0.25f, 0.95f)),
                    radius = radius.dp.toPx(),
                    center = Offset(
                        x = size.width * xPct,
                        y = size.height * yPct
                    )
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            } else {
                Box(modifier = Modifier.padding(start = 48.dp))
            }

            Text(
                text = title,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = BubblyFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = if (onBackClick != null) 48.dp else 0.dp)
            )
        }
    }
}
