package com.example.interfaz_mesames.screen


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.navigation.AppScreen
import kotlinx.coroutines.delay


@Composable
fun CoverScreen(navController: NavController){
    val fondos = listOf(
        R.drawable.portada1,
        R.drawable.portada2
    )

    var indiceBackground by rememberSaveable { mutableIntStateOf(0) }
    var showContent by remember { mutableStateOf(false) }

    // Efecto para cambio automático
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000) // Cambia cada 5 segundos (ajusta este valor según necesites)
            indiceBackground = (indiceBackground + 1) % fondos.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { showContent = true }
    ) {
        // Imagen de fondo con animación Crossfade
        Crossfade(
            targetState = indiceBackground,
            modifier = Modifier.fillMaxSize()
        ) { indice ->
            Image(
                painter = painterResource(id = fondos[indice]),
                contentDescription = "Fondo de pantalla",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Button(
                onClick = { navController.navigate(route = AppScreen.LoginScreen.route) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones1))
            ) {
                Text(
                    text= "Iniciar sesión",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.roboto))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(route = AppScreen.RegistroScreen.route) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones2))
            ) {
                Text(
                    text= "Crear cuenta",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.roboto))
                )
            }

        }

    }

}