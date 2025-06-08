package com.example.interfaz_tfg.compose.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.getRolFromToken

@Composable
fun UserScreenHeader(
    navController: NavController,
    token: String?,
    username: String?
) {
    val userRol = token?.let { getRolFromToken(it) }
    Header(
        navController = navController,
        title = "Perfil",
        back = false,
        route = AppScreen.HomeScreen.route + "/$username/$userRol/$token"
    )
}