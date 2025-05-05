package com.example.interfaz_mesames.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.navigation.AppScreen

@Composable
fun LoadScreen(navController: NavController){

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000) //Para pruebas. Original 5000
        navController.navigate(route = AppScreen.PortadaScreen.route)
    }

    Box(){
        Image(painter = painterResource(R.drawable.fondo_load),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(top = 35.dp)
                .fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.height(220.dp))
            Image(painter = painterResource(R.drawable.icon),
                contentDescription = "Icono",
                modifier = Modifier.size(120.dp)
            )
            Spacer(Modifier.height(30.dp))
            Text(text = "Mes A Mes",
                fontFamily = FontFamily(Font(R.font.charm, FontWeight.Bold)),
                fontSize = 50.sp,
                color = colorResource(R.color.titulo)
            )
            Spacer(Modifier.height(10.dp))
            Text(text = "Conoce tu ciclo, equilibra tu bienestar",
                fontFamily = FontFamily(Font(R.font.arimo, FontWeight.Light)),
                fontSize = 20.sp,
                color = colorResource(R.color.eslogan)
            )

            Spacer(Modifier.height(200.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .padding(top = 30.dp),
                color = Color.Magenta,
                trackColor = Color.White
            )
            
        }
    }
}