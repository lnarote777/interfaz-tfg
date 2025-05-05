package com.example.interfaz_mesames.screen


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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.compose.Header
import com.example.interfaz_mesames.compose.configuraciones.SettingItem
import com.example.interfaz_mesames.navigation.AppScreen

@Composable
fun UserScreen(
    navController: NavController,
    email: String = "usuario@gmail.com",
    periodDuration: Int = 6,
    cycleDuration: Int = 31
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 30.dp, bottom = 18.dp)
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
                        contentDescription = "",
                        modifier = Modifier.size(130.dp)
                    )
                    Text("Username",
                        fontSize = 25.sp)
                    Text("email@gmail.com",
                        fontSize = 20.sp)
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
                    SettingItem(title = "Mi objetivo", "Seguimiento") // cambiar
                    SettingItem(title = "Duración periodo", "6") // cambiar
                    SettingItem(title = "Duración ciclo", "31") // cambiar
                    Button(
                        onClick = {navController.navigate(route = AppScreen.CicloAjustesScreen.route)},
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
                        modifier = Modifier.clickable { navController.navigate(AppScreen.PortadaScreen.route) }
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