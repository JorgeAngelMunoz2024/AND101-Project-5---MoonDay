package com.example.moonday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moonday.ui.MoonViewModel
import com.example.moonday.ui.screens.CalendarSelectScreen
import com.example.moonday.ui.screens.HomeScreen
import com.example.moonday.ui.screens.MoonDetailsScreen

enum class AppScreen {
    HOME,
    CALENDAR,
    DETAILS
}

val BubblyFontFamily = FontFamily(
    Font(R.font.fredoka, FontWeight.Normal),
    Font(R.font.fredoka, FontWeight.Medium),
    Font(R.font.fredoka, FontWeight.Bold),
    Font(R.font.fredoka, FontWeight.ExtraBold)
)

class MainActivity : ComponentActivity() {

    private val viewModel: MoonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoonDayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0D0E15)
                ) {
                    MoonDayApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MoonDayApp(viewModel: MoonViewModel) {
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    var lastSelectedYear by remember { mutableStateOf(2026) }
    var lastSelectedMonth by remember { mutableStateOf(4) }
    var lastSelectedDay by remember { mutableStateOf(15) }

    val uiState by viewModel.uiState.collectAsState()

    when (currentScreen) {
        AppScreen.HOME -> {
            HomeScreen(
                onEnterDateClick = {
                    currentScreen = AppScreen.CALENDAR
                }
            )
        }

        AppScreen.CALENDAR -> {
            CalendarSelectScreen(
                onDateConfirmed = { year, month, day ->
                    lastSelectedYear = year
                    lastSelectedMonth = month
                    lastSelectedDay = day
                    viewModel.fetchMoonPhaseForDate(year, month, day)
                    currentScreen = AppScreen.DETAILS
                },
                onBackClick = {
                    currentScreen = AppScreen.HOME
                }
            )
        }

        AppScreen.DETAILS -> {
            MoonDetailsScreen(
                uiState = uiState,
                onBackClick = {
                    currentScreen = AppScreen.CALENDAR
                },
                onSelectAnotherDateClick = {
                    currentScreen = AppScreen.CALENDAR
                },
                onRetryClick = {
                    viewModel.fetchMoonPhaseForDate(lastSelectedYear, lastSelectedMonth, lastSelectedDay)
                }
            )
        }
    }
}

@Composable
fun MoonDayTheme(content: @Composable () -> Unit) {
    val darkColors = darkColorScheme(
        primary = Color(0xFF6C5CE7),
        background = Color(0xFF0D0E15),
        surface = Color(0xFF171822),
        onPrimary = Color.White,
        onBackground = Color.White,
        onSurface = Color.White
    )

    val bubblyTypography = Typography(
        displayLarge = TextStyle(fontFamily = BubblyFontFamily, fontWeight = FontWeight.Bold, fontSize = 48.sp),
        titleLarge = TextStyle(fontFamily = BubblyFontFamily, fontWeight = FontWeight.Bold, fontSize = 28.sp),
        titleMedium = TextStyle(fontFamily = BubblyFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
        bodyLarge = TextStyle(fontFamily = BubblyFontFamily, fontWeight = FontWeight.Normal, fontSize = 18.sp),
        bodyMedium = TextStyle(fontFamily = BubblyFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        labelLarge = TextStyle(fontFamily = BubblyFontFamily, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    )

    MaterialTheme(
        colorScheme = darkColors,
        typography = bubblyTypography,
        content = content
    )
}
