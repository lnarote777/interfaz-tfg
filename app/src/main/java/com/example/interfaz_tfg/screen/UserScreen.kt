package com.example.interfaz_tfg.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.api_tfg.model.Goal
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.configuraciones.SettingItem
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.screen.settings.UserSettingsScreen
import com.example.interfaz_tfg.viewModel.CycleViewModel

@Composable
fun UserScreen(
    navController: NavController,
    username: String?,
    email: String?
) {
    val viewModel : CycleViewModel = viewModel()
    val cycles = viewModel.cycles.collectAsState()
    val cycle = cycles.value.find { !it.isPredicted }
    val periodDuration = cycle?.bleedingDuration.toString()
    val cycleDuration = cycle?.cycleLength.toString()
    val goal: Goal = Goal.TRACK_PERIOD
    var goalStr = ""



    if (goal == Goal.GET_PREGNANT){
        goalStr = "Quedar embarazada"
    }else if (goal == Goal.TRACK_PERIOD){
        goalStr = "Seguimiento general"
    }else if (goal == Goal.AVOID_PREGNANCY){
        goalStr = "Evitar embarazo"
    }

    LaunchedEffect(email) {
        email?.let {
            viewModel.loadCycles(it)
        }
    }


    Scaffold { innerpadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerpadding)
                .background(color = colorResource(R.color.fondo))
        ) {
            Header(navController, "Perfil")

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(top = 30.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clip(RoundedCornerShape(30.dp))
                        .background(color = Color.White)
                        .height(250.dp)
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        //
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile image",
                            modifier = Modifier.size(130.dp).clickable { navController.navigate(route = AppScreen.UserSettingsScreen.route) },
                            tint = Color.Black
                        )
                        username?.let {
                            Text(it,
                                fontSize = 25.sp,
                                color = Color.Black)
                        }

                        email?.let {
                            Text(it,
                                fontSize = 20.sp,
                                color = Color.Black)
                        }

                    }
                }
                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clip(RoundedCornerShape(30.dp))
                        .background(color = Color.White)
                        .height(250.dp)
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        SettingItem(title = "Mi objetivo", goalStr) // cambiar
                        SettingItem(title = "Duración periodo", periodDuration)
                        SettingItem(title = "Duración ciclo", cycleDuration) //
                        Button(
                            onClick = {navController.navigate(route = AppScreen.CycleSettingsScreen.route + "/$periodDuration/$cycleDuration")},
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones2)),
                            modifier = Modifier.width(150.dp).padding(top = 15.dp)
                        ) {
                            Text("Editar",
                                fontSize = 20.sp)
                        }

                    }
                }
                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clip(RoundedCornerShape(30.dp))
                        .background(color = Color.White)
                        .height(90.dp)
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Cerrar sesión",
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { navController.navigate(AppScreen.CoverScreen.route) }
                        )
                        Spacer(Modifier.height(10.dp))
                        Text("Eliminar cuenta",
                            fontSize = 20.sp,
                            color = Color.Red,
                            modifier = Modifier.clickable {  }
                        )

                    }
                }

            }
        }
    }


}