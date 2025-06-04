package com.example.interfaz_tfg.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.interfaz_tfg.api.model.cycle.CyclePhaseDay
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycle
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
import com.example.interfaz_tfg.viewModel.CalendarSharedViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(calendarSharedViewModel: CalendarSharedViewModel){
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

        composable(AppScreen.CalendarScreen.route + "/{logs}",
            arguments = listOf(
                //navArgument(name = "confirmedCycle") {
                //    type = NavType.StringType
                //},
                //navArgument(name = "predictedCycle") {
                //    type = NavType.StringType
                //},
                navArgument(name = "logs") {
                    type = NavType.StringType
                }
            )
        ){
            val gson = Gson()
            val type = object : TypeToken<List<DailyLog>>() {}.type

            //val confirmedJson = it.arguments?.getString("confirmedCycle") ?: "[]"
            //val predictedJson = it.arguments?.getString("predictedCycle") ?: "[]"
            val logsJson = it.arguments?.getString("logs") ?: "[]"

            //val confirmedPhases: List<CyclePhaseDay> = gson.fromJson(confirmedJson, type)
            //val predictedPhases: List<CyclePhaseDay> = gson.fromJson(predictedJson, type)
            val logs: List<DailyLog> = gson.fromJson(logsJson, type)

            CalendarScreen(
                navController = navController,
                //confirmedPhases = confirmedPhases,
                //predictedPhases = predictedPhases,
                logs = logs,
                calendarSharedViewModel
            )
        }

        composable(AppScreen.SettingsScreen.route){
            SettingsScreen(navController)
        }

        composable(AppScreen.DailyScreen.route + "/{email}/{token}/{isBleeding}",
            arguments = listOf(
                navArgument(name = "email"){
                    type = NavType.StringType
                },
                navArgument(name = "token"){
                    type = NavType.StringType
                },
                navArgument(name = "isBleeding") {
                    type = NavType.StringType
                }
            )
        ){
            DailyScreen(
                navController = navController,
                email = it.arguments?.getString("email")?: "",
                token = it.arguments?.getString("token") ?: "",
                isBleeding = it.arguments?.getString("isBleeding") ?: ""
            )
        }

        composable(AppScreen.HomeScreen.route + "/{username}/{userRol}/{token}",
            arguments = listOf(
                navArgument(name = "username"){
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
                username = it.arguments?.getString("username"),
                token = it.arguments?.getString("token") ?: "",
                userRol = it.arguments?.getString("userRol"),
                calendarSharedViewModel = calendarSharedViewModel
            )
        }

        composable(AppScreen.LoadScreen.route){
            LoadScreen(navController)
        }
        composable("cover") {
            CoverScreen(navController = navController)
        }
        composable("home/{username}/{rol}/{token}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val rol = backStackEntry.arguments?.getString("rol") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            HomeScreen(navController, username, rol, token, calendarSharedViewModel)
        }

        composable(AppScreen.PremiumScreen.route + "/{email}",
            arguments = listOf(
                navArgument(name = "email"){
                    type = NavType.StringType
                }
            )
        ){
            PremiumScreen(navController,
                email = it.arguments?.getString("email") ?: ""
            )
        }

        composable(AppScreen.UserScreen.route + "/{username}/{email}",
            arguments = listOf(
                navArgument(name = "username"){
                    type = NavType.StringType
                },
                navArgument(name = "email"){
                    type = NavType.StringType
                }
            )){

            UserScreen(navController,
                username = it.arguments?.getString("username") ?: "",
                email = it.arguments?.getString("email") ?: ""
            )
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

        composable(AppScreen.CycleSettingsScreen.route+ "/{username}/{email}",
            arguments = listOf(
                navArgument(name = "username"){
                    type = NavType.StringType
                },
                navArgument(name = "email"){
                    type = NavType.StringType
                }
            )){
            CycleSettingsScreen(navController,
                username = it.arguments?.getString("username") ?: "",
                email = it.arguments?.getString("email") ?: ""
            )
        }

    }
}
