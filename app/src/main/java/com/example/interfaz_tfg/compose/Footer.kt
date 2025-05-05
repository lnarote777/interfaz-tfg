package com.example.interfaz_mesames.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.navigation.AppScreen


@Composable
fun Footer(navController: NavController, modifier: Modifier = Modifier) {
    val navColor = colorResource(R.color.navMenu)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
        ) {
            val width = size.width
            val height = size.height
            val notchRadius = 35.dp.toPx()
            val centerX = width / 2
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(centerX - notchRadius * 1.5f, 0f)

                cubicTo(
                    centerX - notchRadius, 0f,
                    centerX - notchRadius, notchRadius * 1.2f,
                    centerX, notchRadius * 1.2f
                )
                cubicTo(
                    centerX + notchRadius, notchRadius * 1.2f,
                    centerX + notchRadius, 0f,
                    centerX + notchRadius * 1.5f, 0f
                )

                lineTo(width, 0f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

            drawPath(
                path = path,
                color = navColor
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {navController.navigate(route = AppScreen.HomeScreen.route)}) {
                Icon(Icons.Default.Home,
                    contentDescription = "Home",
                    tint = colorResource(R.color.iconosNav),
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(onClick = {navController.navigate(route = AppScreen.CalendarScreen.route)}) {
                Icon(Icons.Default.DateRange,
                    contentDescription = "Calendar",
                    tint = colorResource(R.color.iconosNav),
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(50.dp))
            IconButton(onClick = {navController.navigate(route = AppScreen.StatsScreen.route)}) {
                Icon(Icons.Default.Star,
                    contentDescription = "Stats",
                    tint = colorResource(R.color.iconosNav),
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(onClick = {navController.navigate(route = AppScreen.ConfiguracionScreen.route)}) {
                Icon(Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = colorResource(R.color.iconosNav),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Button(
            onClick = {navController.navigate(route = AppScreen.DailyScreen.route)},
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones2)),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-15).dp)
                .zIndex(1f)
                .shadow(elevation = 8.dp, shape = CircleShape)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
