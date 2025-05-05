package com.example.interfaz_mesames.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R


@Composable
fun Header(navController: NavController, title: String, back: Boolean = true, route: String = ""){

    Box(
        modifier = Modifier.fillMaxWidth()
            .background(color = Color.White)
            .height(80.dp)
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                drawLine(
                color = Color.Black,
                start = Offset(0f, size.height - strokeWidth / 2),
                end = Offset(size.width, size.height - strokeWidth / 2),
                strokeWidth = strokeWidth
            ) }
    ){

        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 10.dp)
                .clickable { if (back) navController.popBackStack() else navController.navigate(route = route)}
        )

        Text( title,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.lexend)),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

    }
}