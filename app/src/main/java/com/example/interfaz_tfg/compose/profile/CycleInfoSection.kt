package com.example.interfaz_tfg.compose.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.compose.configuraciones.SettingCard
import com.example.interfaz_tfg.compose.configuraciones.SettingItem
import com.example.interfaz_tfg.compose.profile.profileDataClasses.UserInfo
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.utils.toDisplayString

@Composable
fun CycleInfoSection(
    userInfo: UserInfo,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingCard(height = 250.dp) {
            SettingItem("Mi objetivo", userInfo.goal.toDisplayString())
            SettingItem("Duración periodo", userInfo.periodDuration)
            SettingItem("Duración ciclo", userInfo.cycleDuration)
            
            Button(
                onClick = {
                    navController.navigate(
                        "${AppScreen.CycleSettingsScreen.route}/${userInfo.cycleDuration}/${userInfo.periodDuration}/${userInfo.username}/${userInfo.email}"
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.botones2)
                ),
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 15.dp)
            ) {
                Text("Editar", fontSize = 20.sp)
            }
        }
    }
}