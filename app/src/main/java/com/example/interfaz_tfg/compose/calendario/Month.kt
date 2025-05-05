package com.example.interfaz_mesames.compose.calendario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfaz_mesames.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Month(
    year: Int,
    month: Month,
    selectedDate: LocalDate,
    currentDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val daysInMonth = month.length(Year.of(year).isLeap)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek
    val today = currentDate
    val isCurrentMonth = year == today.year && month == today.month

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(color = Color.White)
    ) {
        // Header del mes
        Text(
            text = "${month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $year".capitalize(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.lexend, FontWeight.SemiBold))
        )

        // Días de la semana
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DayOfWeek.entries.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).capitalize(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = colorResource(R.color.botones2),
                    fontFamily = FontFamily(Font(R.font.lexend, FontWeight.SemiBold))
                )
            }
        }

        var currentWeek = 0
        val weeksInMonth = ((firstDayOfWeek.value - 1 + daysInMonth) / 7) + 1

        while (currentWeek < weeksInMonth) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (dayInWeek in 0 until 7) {
                    val dayOffset = currentWeek * 7 + dayInWeek
                    val day = dayOffset - (firstDayOfWeek.value - 1) + 1

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                    ) {
                        if (day in 1..daysInMonth) {
                            val date = LocalDate.of(year, month, day)
                            val isSelected = date == selectedDate
                            val isToday = date == today

                            val backgroundColor = when {
                                isSelected -> colorResource(R.color.botones2)
                                isToday -> colorResource(R.color.botones2).copy(alpha = 0.3f)
                                else -> Color.Transparent
                            }

                            val textColor = when {
                                isSelected -> MaterialTheme.colorScheme.onPrimary
                                isToday -> colorResource(R.color.botones2)
                                else -> MaterialTheme.colorScheme.onSurface
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = backgroundColor,
                                        shape = CircleShape
                                    )
                                    .clickable { onDateSelected(date) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day.toString(),
                                    color = textColor,
                                    fontWeight = if (isSelected || isToday) FontWeight.Bold
                                    else FontWeight.Normal,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.lexend))
                                )
                            }
                        }
                    }
                }
                currentWeek++
            }
        }
    }
}