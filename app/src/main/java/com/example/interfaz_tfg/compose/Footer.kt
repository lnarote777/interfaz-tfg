package com.example.interfaz_tfg.compose

import android.net.Uri
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.api.model.cycle.CyclePhaseDay
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.getRolFromToken
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun Footer(
    navController: NavController,
    modifier: Modifier = Modifier,
    confirmedPhases: List<CyclePhaseDay>,
    predictedPhases: List<CyclePhaseDay>,
    email: String,
    token: String,
    isBleeding: Boolean,
    logs: List<DailyLog>
) {
    val navColor = colorResource(R.color.navMenu)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val gson = Gson()

    val confirmedJson = remember(confirmedPhases) {
        Uri.encode(gson.toJson(confirmedPhases))
    }
    val predictedJson = remember(predictedPhases) {
        Uri.encode(gson.toJson(predictedPhases))
    }
    val logsJson = remember(logs) {
        Uri.encode(gson.toJson(logs))
    }

    val rol = getRolFromToken(token)
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
            IconButton(onClick = {
                if (currentRoute != AppScreen.HomeScreen.route){
                    navController.navigate(route = AppScreen.HomeScreen.route)
                }

            }) {
                Icon(Icons.Default.Home,
                    contentDescription = "Home",
                    tint = colorResource(R.color.iconosNav),
                    modifier = Modifier.size(40.dp)
                )
            }

            IconButton(onClick = {
                navController.navigate(AppScreen.CalendarScreen.route + "/$logsJson")
            }) {
                Icon(Icons.Default.DateRange,
                    contentDescription = "Calendar",
                    tint = colorResource(R.color.iconosNav),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(50.dp))

            IconButton(onClick = {
                if(rol != "PREMIUM"){
                    navController.navigate(route = AppScreen.PremiumScreen.route + "/$email")
                }else{
                    navController.navigate(route = AppScreen.StatsScreen.route)
                }
            }) {
                Icon(Icons.Default.Star,
                    contentDescription = "Stats",
                    tint = colorResource(R.color.iconosNav),
                    modifier = Modifier.size(40.dp)
                )
            }

            IconButton(onClick = {navController.navigate(route = AppScreen.SettingsScreen.route)}) {
                Icon(Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = colorResource(R.color.iconosNav),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Button(
            onClick = {navController.navigate("${AppScreen.DailyScreen.route}/$email/$token/$isBleeding")},
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
