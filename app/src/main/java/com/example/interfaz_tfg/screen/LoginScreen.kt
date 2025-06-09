package com.example.interfaz_tfg.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.compose.TextFielPassword
import com.example.interfaz_tfg.compose.Textfield
import com.example.interfaz_tfg.compose.login.LoginScreenEffects
import com.example.interfaz_tfg.compose.login.LoginScreenUI
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginFormData
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginScreenState
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.utils.performLogin
import com.example.interfaz_tfg.viewModel.LoginViewModel


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
){
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    var user by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passVisible by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var showError by rememberSaveable { mutableStateOf(false) }

    var screenState = remember(user, password, passVisible, errorMessage, showError) {
        LoginScreenState(
            user = user,
            password = password,
            passVisible = passVisible,
            errorMessage = errorMessage,
            showError = showError
        )
    }
    val formData = remember(screenState) {
        LoginFormData.from(screenState)
    }

    LoginScreenEffects(
        uiState = uiState,
        onErrorReceived = { errorMsg ->
                errorMessage = errorMsg
                showError = true
                user = ""
                password = ""

        }
    )

    LoginScreenUI(
        navController = navController,
        screenState = screenState,
        formData = formData,
        onStateChange = { newState ->
            user = newState.user
            password = newState.password
            passVisible = newState.passVisible
            errorMessage = newState.errorMessage
            showError = newState.showError
        },
        onLogin = {
            performLogin(
                context = context,
                formData = formData,
                viewModel = viewModel,
                navController = navController,
                onValidationError = { errorMsg ->
                    screenState = screenState.copy(
                        errorMessage = errorMsg,
                        showError = true
                    )
                }
            )
        }
    )
}