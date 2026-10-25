package com.example.moonday.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moonday.BubblyFontFamily
import com.example.moonday.data.model.MoonPhaseResponse
import com.example.moonday.ui.MoonUiState
import com.example.moonday.ui.components.MoonSvgView
import com.example.moonday.ui.components.StarryHeader

enum class DetailsTab(val title: String, val icon: ImageVector) {
    PHASE("Phase", Icons.Default.Nightlight),
    ASTRONOMY("Astronomy", Icons.Default.Public),
    GUIDANCE("Guidance", Icons.Default.AutoAwesome),
    FORECAST("Forecast", Icons.Default.DateRange)
}

@Composable
fun MoonDetailsScreen(
    uiState: MoonUiState,
    onBackClick: () -> Unit,
    onSelectAnotherDateClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabOrdinal by remember { mutableIntStateOf(DetailsTab.PHASE.ordinal) }
    val currentTab = DetailsTab.entries[selectedTabOrdinal]

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFF0D0E15),
        topBar = {
            StarryHeader(
                title = "Moon Details",
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            if (uiState is MoonUiState.Success) {
                NavigationBar(
                    containerColor = Color(0xFF171822),
                    tonalElevation = 8.dp
                ) {
                    DetailsTab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = currentTab == tab,
                            onClick = { selectedTabOrdinal = tab.ordinal },
                            icon = { Icon(tab.icon, contentDescription = tab.title) },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 14.sp,
                                    fontFamily = BubblyFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                indicatorColor = Color(0xFF6C5CE7),
                                unselectedIconColor = Color(0xFFA0A5C0),
                                unselectedTextColor = Color(0xFFA0A5C0)
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            when (uiState) {
                is MoonUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF6C5CE7),
                            strokeWidth = 5.dp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Querying FreeAstroAPI...",
                            fontSize = 20.sp,
                            fontFamily = BubblyFontFamily,
                            color = Color(0xFFA0A5C0)
                        )
                    }
                }

                is MoonUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF171822)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Unable to load data",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = BubblyFontFamily,
                                    color = Color(0xFFFF7675)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = uiState.message,
                                    fontSize = 18.sp,
                                    fontFamily = BubblyFontFamily,
                                    color = Color(0xFFA0A5C0),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(
                                    onClick = onRetryClick,
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C5CE7)),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        text = "Retry",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = BubblyFontFamily
                                    )
                                }
                            }
                        }
                    }
                }

                is MoonUiState.Success -> {
                    val data = uiState.response
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        data.timestamp?.let { ts ->
                            Text(
                                text = "Date: $ts",
                                fontSize = 18.sp,
                                fontFamily = BubblyFontFamily,
                                color = Color(0xFFA0A5C0),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        when (currentTab) {
                            DetailsTab.PHASE -> PhaseTabContent(data)
                            DetailsTab.ASTRONOMY -> AstronomyTabContent(data)
                            DetailsTab.GUIDANCE -> GuidanceTabContent(data)
                            DetailsTab.FORECAST -> ForecastTabContent(data)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onSelectAnotherDateClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6C5CE7),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Select Another Date",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = BubblyFontFamily
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun PhaseTabContent(data: MoonPhaseResponse) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF171822)),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val svgData = data.moonVisual?.svg
            if (!svgData.isNullOrEmpty()) {
                MoonSvgView(
                    svgContent = svgData,
                    height = 250.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = data.phase?.name ?: "Moon Phase",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = BubblyFontFamily,
                color = Color.White
            )

            data.phase?.illumination?.let { illum ->
                val illumPercent = (illum * 100).toInt()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$illumPercent% Illuminated",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BubblyFontFamily,
                    color = Color(0xFF6C5CE7)
                )
            }

            data.traditionalMoon?.name?.let { traditionalName ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Traditional Name: $traditionalName",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BubblyFontFamily,
                    color = Color(0xFFFDCB6E)
                )
            }

            data.specialMoon?.labels?.let { labels ->
                if (labels.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Special Features: ${labels.joinToString(", ")}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = BubblyFontFamily,
                        color = Color(0xFF74B9FF)
                    )
                }
            }
        }
    }
}

@Composable
private fun AstronomyTabContent(data: MoonPhaseResponse) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF171822)),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Astronomical & Zodiac Details",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = BubblyFontFamily,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            data.phase?.let { p ->
                InfoRow("Moon Age", "${p.ageDays ?: "-"} days")
                InfoRow("Distance", "${p.distanceKm ?: "-"} km")
                InfoRow("Phase Angle", "${p.phaseAngleDeg ?: "-"}°")
                InfoRow("Waxing/Waning", if (p.isWaxing == true) "Waxing" else "Waning")
            }

            data.zodiac?.let { z ->
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 14.dp),
                    color = Color(0xFF2A2B3D)
                )
                InfoRow("Tropical Zodiac", "${z.sign ?: "-"} (${z.degree ?: "-"}°)")
            }
        }
    }
}

@Composable
private fun GuidanceTabContent(data: MoonPhaseResponse) {
    data.interpretation?.let { interp ->
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF171822)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = interp.title ?: "Astrological Guidance",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = BubblyFontFamily,
                    color = Color(0xFF74B9FF),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = interp.body ?: "",
                    fontSize = 18.sp,
                    fontFamily = BubblyFontFamily,
                    color = Color(0xFFDFE6E9),
                    lineHeight = 28.sp
                )
            }
        }
    } ?: run {
        Text(
            text = "No guidance text available for this date.",
            fontSize = 18.sp,
            fontFamily = BubblyFontFamily,
            color = Color(0xFFA0A5C0),
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
private fun ForecastTabContent(data: MoonPhaseResponse) {
    data.nextPhases?.let { next ->
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF171822)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Upcoming Lunar Phases",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = BubblyFontFamily,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                next.newMoon?.let { InfoRow("Next New Moon", it) }
                next.firstQuarter?.let { InfoRow("First Quarter", it) }
                next.fullMoon?.let { InfoRow("Next Full Moon", it) }
                next.lastQuarter?.let { InfoRow("Last Quarter", it) }
            }
        }
    } ?: run {
        Text(
            text = "No forecast data available for this date.",
            fontSize = 18.sp,
            fontFamily = BubblyFontFamily,
            color = Color(0xFFA0A5C0),
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 20.sp, fontFamily = BubblyFontFamily, color = Color(0xFFA0A5C0))
        Text(text = value, fontSize = 20.sp, fontFamily = BubblyFontFamily, fontWeight = FontWeight.Bold, color = Color.White)
    }
}
