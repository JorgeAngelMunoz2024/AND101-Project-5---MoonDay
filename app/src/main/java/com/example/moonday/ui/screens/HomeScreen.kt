package com.example.moonday.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moonday.BubblyFontFamily
import com.example.moonday.ui.components.MoonAnimationView

@Composable
fun HomeScreen(
    onEnterDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0D0E15))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text(
                text = "MoonDay",
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = BubblyFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Discover the moon phase, astrological insights, and lunar visuals for any day of the year.",
                fontSize = 20.sp,
                fontFamily = BubblyFontFamily,
                color = Color(0xFFA0A5C0),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        MoonAnimationView(size = 220.dp)

        Button(
            onClick = onEnterDateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C5CE7),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Enter a Date",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BubblyFontFamily
            )
        }
    }
}
