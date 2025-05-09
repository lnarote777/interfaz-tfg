package com.example.interfaz_tfg.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.configuraciones.SettingItem
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.R

@Composable
fun SettingsScreen(navController: NavController){

    Scaffold { innerpadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerpadding)

        ) {
            Header(navController, "Ajustes")
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .padding(top = 30.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(color = Color.White)

            ){
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    SettingItem(title = "Idioma", value = "Español")
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Unidades", value = "Kg/cm")
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Apariencia", value = "Sistema")
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Recordatorios", isClickable = true)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Tema", isClickable = true)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Actualizar a premium", isClickable = true, navController = navController, route = AppScreen.PremiumScreen.route)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Ayuda y comentarios", isClickable = true)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Privacidad", isClickable = true)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Configuración de privacidad", isClickable = true)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Sobre nosotros", isClickable = true)
                }
            }
        }
    }

}