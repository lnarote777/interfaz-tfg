package com.example.interfaz_tfg

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.navigation.buildTestNavGraph
import com.example.interfaz_tfg.screen.CoverScreen
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoverScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(composeTestRule.activity)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            navController.graph = buildTestNavGraph(navController)
            CoverScreen(navController = navController)
        }
    }

    @Test
    fun clickingLoginButton_navigatesToLogin() {
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        assertEquals(AppScreen.LoginScreen.route, navController.currentDestination?.route)
    }

    @Test
    fun clickingRegisterButton_navigatesToRegister() {
        composeTestRule.onNodeWithText("Crear cuenta").performClick()
        assertEquals(AppScreen.RegisterScreen.route, navController.currentDestination?.route)
    }
}