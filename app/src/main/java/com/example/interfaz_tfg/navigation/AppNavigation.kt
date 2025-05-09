package com.example.interfaz_tfg.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.interfaz_tfg.screen.CalendarScreen
import com.example.interfaz_tfg.screen.CoverScreen
import com.example.interfaz_tfg.screen.DailyScreen
import com.example.interfaz_tfg.screen.HomeScreen
import com.example.interfaz_tfg.screen.LoadScreen
import com.example.interfaz_tfg.screen.LoginScreen
import com.example.interfaz_tfg.screen.PremiumScreen
import com.example.interfaz_tfg.screen.RegisterScreen
import com.example.interfaz_tfg.screen.StatsScreen
import com.example.interfaz_tfg.screen.UserScreen
import com.example.interfaz_tfg.screen.settings.CycleSettingsScreen
import com.example.interfaz_tfg.screen.settings.SettingsScreen
import com.example.interfaz_tfg.screen.settings.UserSettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = AppScreen.LoadScreen.route,
        enterTransition = {
            slideInVertically(initialOffsetY = { it }) + fadeIn()
        },
        exitTransition = {
            slideOutVertically(targetOffsetY = { -it }) + fadeOut()
        },
        popEnterTransition = {
            slideInVertically(initialOffsetY = { -it }) + fadeIn()
        },
        popExitTransition = {
            slideOutVertically(targetOffsetY = { it }) + fadeOut()
        }
    ) {
        composable(AppScreen.CoverScreen.route){
            CoverScreen(navController)
        }

        composable(AppScreen.LoginScreen.route){
            LoginScreen(navController)
        }

        composable(AppScreen.RegisterScreen.route){
            RegisterScreen(navController)
        }

        composable(AppScreen.CalendarScreen.route){
            CalendarScreen(navController)
        }

        composable(AppScreen.SettingsScreen.route){
            SettingsScreen(navController)
        }

        composable(AppScreen.DailyScreen.route){
            DailyScreen(navController)
        }

        composable(AppScreen.HomeScreen.route + "/{user}/{userRol}/{token}",
            arguments = listOf(
                navArgument(name = "user"){
                    type = NavType.StringType
                },
                navArgument(name = "userRol"){
                    type = NavType.StringType
                },
                navArgument(name = "token") {
                    type = NavType.StringType
                }
            )
        ){
            HomeScreen(
                navController = navController,
                user = it.arguments?.getString("user"),
                token = it.arguments?.getString("token") ?: "",
                userRol = it.arguments?.getString("userRol")
            )
        }

        composable(AppScreen.LoadScreen.route){
            LoadScreen(navController)
        }

        composable(AppScreen.PremiumScreen.route){
            PremiumScreen(navController)
        }

        composable(AppScreen.UserScreen.route){
            UserScreen(navController)
        }

        composable(AppScreen.UserSettingsScreen.route+ "/{username}/{email}",
            arguments = listOf(
                navArgument(name = "username"){
                    type = NavType.StringType
                },
                navArgument(name = "email"){
                    type = NavType.StringType
                }
            )
        ){
            UserSettingsScreen(
                navController,
                username = it.arguments?.getString("username") ?: "",
                email = it.arguments?.getString("email") ?: ""
            )
        }

        composable(AppScreen.StatsScreen.route){
            StatsScreen(navController)
        }

        composable(AppScreen.CycleSettingsScreen.route){
            CycleSettingsScreen(navController)
        }


/*
        composable(AppScreen.HomeScreen.route + "/{user}/{userRol}/{token}",
            arguments = listOf(
                navArgument(name = "user"){
                type = NavType.StringType
                },
                navArgument(name = "userRol"){
                type = NavType.StringType
                },
                navArgument(name = "token") {
                type = NavType.StringType
                }
            )
        ){
            HomeScreen(
                navController = navController,
                user = it.arguments?.getString("user"),
                token = it.arguments?.getString("token") ?: "",
                userRol = it.arguments?.getString("userRol")
            )
        }
*/

    }
}
