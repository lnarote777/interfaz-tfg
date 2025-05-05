package com.example.interfaz_mesames.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.compose.Header
import com.example.interfaz_mesames.compose.configuraciones.SettingItem
import com.example.interfaz_mesames.navigation.AppScreen

@Composable
fun UserSettingsScreen(navController: NavController, username: String? ="", email: String? = ""){
    Header(
        navController = navController,
        title = "Ajustes del perfil",
        back = false,
        route = AppScreen.UserScreen.route
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.fondo))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(30.dp)
                )
                .clip(RoundedCornerShape(30.dp))
                .background(color = Color.White)

        ){
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                SettingItem(title = "Cambiar foto de perfil", value = "") // foto de perfil
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingItem(title = "Cambiar nombre de usuario", value = username)
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingItem(title = "Cambiar email", value = email)
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}