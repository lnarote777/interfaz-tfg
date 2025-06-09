package com.example.interfaz_tfg.navigation

import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.interfaz_tfg.screen.LoginScreen

/**
 * Clase sellada que define las pantallas principales de la aplicación y sus rutas asociadas.
 *
 * Cada objeto dentro de la clase representa una pantalla de la aplicación, y el valor de `route` es la ruta
 * que se utilizará para la navegación en el `NavHost`.
 *
 * Las pantallas definidas son:
 * - [PortadaScreen]: Pantalla de bienvenida al iniciar la aplicación.
 * - [LoginScreen]: Pantalla donde los usuarios pueden ingresar sus credenciales.
 * - [RegisterScreen]: Pantalla para que los usuarios se registren en la aplicación.
 * - [HomeScreen]: Pantalla principal donde se gestionan las tareas y se accede a otras funciones.
 * - [PerfilScreen]: Pantalla donde el usuario puede ver y modificar su perfil.
 */
sealed class AppScreen (val route: String) {

    object CoverScreen: AppScreen("CoverScreen")

    object LoginScreen: AppScreen("LoginScreen")

    object RegisterScreen: AppScreen("RegisterScreen")

    object HomeScreen: AppScreen("HomeScreen")

    object UserScreen: AppScreen("UserScreen")

    object CalendarScreen: AppScreen("CalendarScree")

    object CycleSettingsScreen: AppScreen("CycleSettingsScreen")

    object SettingsScreen: AppScreen("SettingsScreen")

    object DailyScreen: AppScreen("DailyScreen")

    object LoadScreen: AppScreen("LoadScreen")

    object PremiumScreen: AppScreen("PremiumScreen")

    object StatsScreen: AppScreen("StatsScreen")

    object UserSettingsScreen: AppScreen("UserSettingsScreen")

}

fun buildTestNavGraph(navController: NavHostController): NavGraph {
    return navController.createGraph(startDestination = AppScreen.CoverScreen.route) {
        composable(AppScreen.CoverScreen.route) {}
        composable(AppScreen.LoginScreen.route) {}
        composable(AppScreen.RegisterScreen.route) {}
    }
}