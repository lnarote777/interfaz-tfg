package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginFormData
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginScreenState

@Composable
fun LoginFormContainer(
    navController: NavController,
    screenState: LoginScreenState,
    formData: LoginFormData,
    onStateChange: (LoginScreenState) -> Unit,
    onLogin: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(topStart = 51.dp, topEnd = 51.dp))
                .background(colorResource(R.color.tarjetaDatos))
                .height(600.dp)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    LoginFormContent(
                        screenState = screenState,
                        formData = formData,
                        onStateChange = onStateChange,
                        onLogin = onLogin
                    )
                }
                
                item {
                    LoginFooterSection(navController = navController)
                }
            }
        }
    }
}
