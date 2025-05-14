package com.example.interfaz_tfg.compose.calendario

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfaz_tfg.R
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("NewApi")
@Composable
fun WeekCalendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {

    val today = LocalDate.now()
    val color = MaterialTheme.colorScheme
    var weekOffset by remember { mutableStateOf(0) }
    var swipeDirection by remember { mutableStateOf(0) }
    val startOfWeek = today
        .with(DayOfWeek.MONDAY)
        .plusWeeks(weekOffset.toLong())

    val gestureModifier = Modifier
        .pointerInput(weekOffset) {
            detectHorizontalDragGestures { _, dragAmount ->
                if (dragAmount > 50) {
                    swipeDirection = -1
                    weekOffset--
                } else if (dragAmount < -50) {
                    swipeDirection = 1
                    weekOffset++
                }
            }
        }

    AnimatedContent(
        targetState = startOfWeek,
        transitionSpec = {
            if (swipeDirection == 1) {
                slideInHorizontally { width -> width } + fadeIn() with
                        slideOutHorizontally { width -> -width } + fadeOut()
            } else {
                slideInHorizontally { width -> -width } + fadeIn() with
                        slideOutHorizontally { width -> width } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(gestureModifier)
            .padding(vertical = 8.dp)
    ) { animatedStartOfWeek ->
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .then(gestureModifier)
                .padding(vertical = 8.dp)
        ) {
            (0..6).forEach { i ->
                val date = animatedStartOfWeek.plusDays(i.toLong())
                val isSelected = date == selectedDate
                val isToday = date == today

                val backgroundColor = when {
                    isSelected -> colorResource(R.color.botones2)
                    isToday -> colorResource(R.color.botones2).copy(alpha = 0.3f)
                    else -> Color.Transparent
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = date.dayOfWeek.name.take(3),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = date.dayOfMonth.toString(),
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(backgroundColor)
                            .clickable { onDateSelected(date) }
                            .padding(15.dp),
                        color = if (isSelected) Color.White else color.onBackground,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}