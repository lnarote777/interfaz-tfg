package com.example.interfaz_tfg.compose.calendario

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.api.model.cycle.CyclePhase
import com.example.interfaz_tfg.api.model.cycle.CyclePhaseDay
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Month(
    year: Int,
    month: Month,
    selectedDate: LocalDate,
    currentDate: LocalDate,
    confirmedPhases: List<CyclePhaseDay>,
    predictedPhases: List<CyclePhaseDay>,
    logs: List<DailyLog>,
    showNavigationArrows: Boolean,
    onDateSelected: (LocalDate) -> Unit,
    onPreviousMonth: () -> Unit = {},
    onNextMonth: () -> Unit = {}
) {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val daysInMonth = month.length(Year.of(year).isLeap)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek
    val color = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(color = color.surface)
    ) {
        // Header del mes
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (showNavigationArrows) {
                IconButton(modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { onPreviousMonth() }) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Mes anterior",
                        tint = color.onSurface,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Text(
                text = "${month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $year".capitalize(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.lexend, FontWeight.SemiBold)),
                color = color.onSurface
            )

            if (showNavigationArrows) {
                IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { onNextMonth() }) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = "Siguiente mes",
                        tint = color.onSurface,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }


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
                            val isToday = date == currentDate
                            val confirmedPhase = confirmedPhases.find { it.date == date.toString() }
                            val predictedPhase = predictedPhases.find { it.date == date.toString() }
                            val hasConfirmedBleeding =
                                logs.any { it.date == date.toString() && it.hasMenstruation }

                            val baseColor = when {
                                confirmedPhase != null -> {
                                    if (confirmedPhase.phase == CyclePhase.MENSTRUATION) {
                                        if (hasConfirmedBleeding) {
                                            getPhaseColor(CyclePhase.MENSTRUATION, false)
                                        } else {
                                            getPhaseColor(CyclePhase.MENSTRUATION, true)
                                        }
                                    } else {
                                        getPhaseColor(confirmedPhase.phase, false)
                                    }
                                }
                                predictedPhase != null -> {
                                    getPhaseColor(predictedPhase.phase, true)
                                }
                                else -> Color.Transparent
                            }

                            val backgroundColor = when {
                                isSelected -> colorResource(R.color.botones2)
                                !isSelected && isToday && baseColor != Color.Transparent -> baseColor
                                !isSelected && isToday -> colorResource(R.color.botones2).copy(alpha = 0.3f)
                                else -> baseColor
                            }
                            val textColor = when {
                                isSelected -> MaterialTheme.colorScheme.onPrimary
                                isToday && baseColor == Color.Transparent -> MaterialTheme.colorScheme.onPrimary
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

fun getPhaseColor(phase: CyclePhase?, isPredicted: Boolean): Color {
    return when (phase) {
        CyclePhase.MENSTRUATION -> if(!isPredicted) Color(0xFFFF6B6B) else  Color(0xFFFF6B6B).copy(alpha = 0.3f)// rojo
        CyclePhase.OVULATION -> if(!isPredicted) Color(0xFF64B5F6) else  Color(0xFF64B5F6).copy(alpha = 0.3f)
        else -> Color.Transparent
    }
}