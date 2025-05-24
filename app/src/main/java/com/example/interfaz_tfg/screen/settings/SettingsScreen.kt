package com.example.interfaz_tfg.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.configuraciones.SettingItem
import com.example.interfaz_tfg.navigation.AppScreen

@Composable
fun SettingsScreen(navController: NavController){

    var showAboutUs by rememberSaveable { mutableStateOf(false) }
    var showPrivacity by rememberSaveable { mutableStateOf(false) }

    if (showPrivacity){
        AlertDialog(
            onDismissRequest = { showPrivacity = false },
            title = { Text("Privacidad") },
            text = {
                Text(
                    "Tu privacidad es muy importante para nosotros. Esta aplicación ha sido diseñada para garantizar la confidencialidad de tu información personal y los datos relacionados con tu salud.\n\n" +
                            "Los datos que registras, como tus ciclos menstruales, síntomas, estado de ánimo o cualquier otra información, se almacenan de manera segura y nunca serán compartidos con terceros sin tu consentimiento expreso.\n\n" +
                            "Actualmente, no compartimos ni vendemos ningún tipo de información a anunciantes ni terceros. Toda la información se utiliza únicamente para brindarte una experiencia personalizada dentro de la aplicación.\n\n" +
                            "Esta sección es meramente informativa y no representa una configuración activa. Para cualquier duda o inquietud relacionada con la privacidad, puedes contactarnos directamente desde el apartado de soporte.\n\n" +
                            "Seguimos trabajando constantemente para proteger tus datos y darte el control total sobre tu información."
                )
            },
            confirmButton = {
                Button(onClick = {
                    showPrivacity = false
                }) {
                    Text("Aceptar")
                }
            }
        )
    }

    if (showAboutUs){
        AlertDialog(
            onDismissRequest = { showAboutUs = false },
            title = { Text("Sobre nosotros") },
            text = {
                Text(
                    "Somos un equipo comprometido con el bienestar, la salud y la educación menstrual. Nuestra aplicación nace con el propósito de brindar a las personas una herramienta fácil de usar, precisa y segura para seguir su ciclo menstrual, registrar síntomas diarios, detectar patrones y promover un mejor conocimiento del propio cuerpo.\n\n" +
                            "Creemos que el autocuidado comienza con la información, por eso nos esforzamos en ofrecer una experiencia personalizada, basada en ciencia y desarrollada con empatía. Nuestra misión es romper estigmas, normalizar las conversaciones sobre la menstruación y empoderar a nuestras usuarias para tomar decisiones informadas sobre su salud.\n\n" +
                            "Además del seguimiento del ciclo, ofrecemos funcionalidades como el registro de estados de ánimo, actividad física, flujo, peso, y mucho más. Todo está diseñado para ayudarte a entender mejor tu cuerpo y anticiparte a los cambios que experimentas cada mes.\n\n" +
                            "Gracias por confiar en nosotras. Seguiremos mejorando día a día para ofrecerte un acompañamiento útil, respetuoso y honesto en cada etapa de tu vida."
                )
            },
            confirmButton = {
                Button(onClick = {
                    showAboutUs = false
                }) {
                    Text("Aceptar")
                }
            }
        )
    }



    Scaffold { innerpadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerpadding)

        ) {
            Header(navController, "Ajustes")
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .padding(top = 30.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(color = Color.White)

            ){
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    SettingItem(title = "Idioma", value = "Español")
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Apariencia", value = "Sistema")
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Recordatorios", isClickable = true)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Actualizar a premium", isClickable = true, navController = navController, route = AppScreen.PremiumScreen.route)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Configuración de privacidad",
                        isClickable = true,
                        onClick = {showPrivacity = true}
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingItem(title = "Sobre nosotros",
                        isClickable = true,
                        onClick ={showAboutUs = true}
                    )
                }
            }
        }
    }

}