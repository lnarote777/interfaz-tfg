package com.example.interfaz_tfg

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.interfaz_tfg.compose.configuraciones.SettingItem
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.screen.CoverScreen
import com.example.interfaz_tfg.screen.DailyScreen
import com.example.interfaz_tfg.screen.settings.UserSettingsScreen
import okhttp3.internal.tls.OkHostnameVerifier.verify
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


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
                // ver más abajo
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
        // Paso 1: Abrir diálogo para cambiar imagen
        composeTestRule
            .onNodeWithText("Cambiar imagen de perfil")
            .performClick()

        // Paso 2: Clic en "Elegir avatar" dentro del AlertDialog
        composeTestRule
            .onNodeWithText("Elegir avatar")
            .assertIsDisplayed()
            .performClick()

        // Paso 3: Confirmar que el diálogo de avatares aparece
        composeTestRule
            .onNodeWithText("Selecciona un avatar")
            .assertIsDisplayed()

        // Paso 4: Clic en el avatar específico
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

        // Verifica que el botón "Guardar cambios" se muestra
        composeTestRule
            .onNodeWithText("Guardar cambios")
            .assertIsDisplayed()
            .assertHasClickAction()

        // Simula un clic en el botón
        composeTestRule
            .onNodeWithText("Guardar cambios")
            .performClick()
    }

}
