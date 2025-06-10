package com.example.interfaz_tfg


import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.interfaz_tfg.screen.DailyScreen
import com.example.interfaz_tfg.screen.settings.UserSettingsScreen
import org.junit.Rule
import org.junit.Test


class ComponentTests {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun dailyScreen_showsMoodEmojis() {
        composeTestRule.setContent {
            val navController: NavHostController = rememberNavController()

            DailyScreen(
                navController = navController,
                email = "test@example.com",
                token = "fake_token",
                isBleeding = "true",
            )
        }

        composeTestRule.onNodeWithTag("emoji_Feliz").assertIsDisplayed()
        composeTestRule.onNodeWithTag("emoji_Cansada").assertIsDisplayed()
    }

    @Test
    fun testSelectPredefinedProfileImage() {
        composeTestRule.setContent {
            val navController: NavHostController = rememberNavController()
            UserSettingsScreen(
                navController = navController,
                username = "prueba11",
                email = "prueba11@gmail.com"
            )
        }
        composeTestRule
            .onNodeWithText("Cambiar imagen de perfil")
            .performClick()

        composeTestRule
            .onNodeWithText("Elegir avatar")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithText("Selecciona un avatar")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("avatar2")
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun testGuardarCambiosButtonIsVisibleAndClickable() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            UserSettingsScreen(
                navController = navController,
                username = "usuarioPrueba",
                email = "prueba@correo.com"
            )
        }

        composeTestRule
            .onNodeWithText("Guardar cambios")
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithText("Guardar cambios")
            .performClick()
    }

}
