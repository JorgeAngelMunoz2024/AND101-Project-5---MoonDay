package com.example.moonday.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moonday.BubblyFontFamily
import com.example.moonday.ui.components.StarryHeader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarSelectScreen(
    onDateConfirmed: (year: Int, month: Int, day: Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    val selectedMillis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
    val formattedDisplayDate = formatMillisToDisplay(selectedMillis)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0D0E15)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            StarryHeader(
                title = "Pick a Date",
                onBackClick = onBackClick
            )

            Text(
                text = "Select any day in the year to query FreeAstroAPI.",
                fontSize = 18.sp,
                fontFamily = BubblyFontFamily,
                color = Color(0xFFA0A5C0),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF171822)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        containerColor = Color(0xFF171822),
                        titleContentColor = Color.White,
                        headlineContentColor = Color.White,
                        weekdayContentColor = Color(0xFFA0A5C0),
                        dayContentColor = Color.White,
                        selectedDayContainerColor = Color(0xFF6C5CE7),
                        selectedDayContentColor = Color.White,
                        todayDateBorderColor = Color(0xFF6C5CE7),
                        todayContentColor = Color(0xFF6C5CE7)
                    )
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selected Date: $formattedDisplayDate",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BubblyFontFamily,
                color = Color(0xFFE0E0E0),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Button(
                onClick = {
                    val (y, m, d) = extractYearMonthDayUTC(selectedMillis)
                    onDateConfirmed(y, m, d)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6C5CE7),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Fetch Moon Phase",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BubblyFontFamily
                )
            }
        }
    }
}

private fun formatMillisToDisplay(millis: Long): String {
    val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return formatter.format(Date(millis))
}

private fun extractYearMonthDayUTC(millis: Long): Triple<Int, Int, Int> {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        timeInMillis = millis
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return Triple(year, month, day)
}
