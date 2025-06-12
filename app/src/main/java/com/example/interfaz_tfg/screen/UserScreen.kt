package com.example.interfaz_tfg.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.utils.UserPreferences
import com.example.interfaz_tfg.api.model.user.Goal
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.ProfileImage
import com.example.interfaz_tfg.compose.configuraciones.SettingCard
import com.example.interfaz_tfg.compose.configuraciones.SettingItem
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel
import com.example.interfaz_tfg.viewModel.getRolFromToken
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun UserScreen(
    navController: NavController,
    username: String?,
    email: String?
) {
    val colorScheme = MaterialTheme.colorScheme
    val userViewModel: UserViewModel = viewModel()
    val cycleViewModel : CycleViewModel = viewModel()
    val user = userViewModel.user.collectAsState()
    val cycles = cycleViewModel.cycles.collectAsState()
    val cycle = cycles.value.find { !it.isPredicted }
    val periodDuration = cycle?.bleedingDuration.toString()
    val cycleDuration = cycle?.cycleLength.toString()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var confirmationEmail by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    val selectedImageUri by userViewModel.selectedImageUri.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var token by remember { mutableStateOf<String?>(null) }

    val goalStr = when (user.value?.goal) {
        Goal.GET_PREGNANT -> "Quedar embarazada"
        Goal.TRACK_PERIOD -> "Seguimiento general"
        Goal.AVOID_PREGNANCY -> "Evitar embarazo"
        else -> "Sin especificar"
    }


    LaunchedEffect(Unit) {
        token = UserPreferences.getToken(context).firstOrNull()
        userViewModel.initFromPreferences(context)

    }

    LaunchedEffect(email) {
        token = UserPreferences.getToken(context).firstOrNull()
        email?.let {
            cycleViewModel.loadCycles(it)
            if (username != null) {
                token?.let { it1 -> userViewModel.getUserByUsername(it1, username) }
            }
        }
    }

    Scaffold { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .background(color = colorScheme.background)
        ) {
            val userRol = token?.let { getRolFromToken(it) }
            Header(navController, "Perfil", back = false, onClick = {
                navController.navigate(AppScreen.HomeScreen.route + "/$username/$userRol/$token")
            })

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp)
            ) {
                // Tarjeta con imagen de perfil, nombre y email, usando la URI o avatar seleccionado.
                SettingCard(height = 250.dp) {
                    username?.let { userName ->
                        email?.let { userEmail ->
                            ProfileImage(
                                uri = selectedImageUri,
                                avatarRes = userViewModel.selectedAvatarRes.collectAsState().value,
                                navController,
                                userName,
                                userEmail
                            )
                            Text(userName, fontSize = 25.sp, color = Color.Black)
                            Text(userEmail, fontSize = 20.sp, color = Color.Black)
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                // Tarjeta con datos de configuración del ciclo y botón para editar.
                SettingCard(height = 250.dp) {
                    SettingItem("Mi objetivo", goalStr)
                    SettingItem("Duración periodo", periodDuration)
                    SettingItem("Duración ciclo", cycleDuration)
                    Button(
                        onClick = {
                            navController.navigate(
                                "${AppScreen.CycleSettingsScreen.route}/$cycleDuration/$periodDuration/$username/$email"
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones2)),
                        modifier = Modifier
                            .width(150.dp)
                            .padding(top = 15.dp)
                    ) {
                        Text("Editar", fontSize = 20.sp)
                    }
                }
                Spacer(Modifier.height(20.dp))

                // Tarjeta con opciones para cerrar sesión y eliminar cuenta.
                SettingCard(height = 90.dp) {
                    Text(
                        "Cerrar sesión",
                        fontSize = 20.sp,
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                UserPreferences.clearUser(context)
                                navController.navigate(AppScreen.CoverScreen.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        },
                        color = Color.Black
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Eliminar cuenta",
                        fontSize = 20.sp,
                        color = Color.Red,
                        modifier = Modifier.clickable { showDeleteDialog = true }
                    )
                }

            }

            // Diálogo de confirmación para eliminar la cuenta, que pide confirmar el correo.
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("¿Estás seguro?") },
                    text = {
                        Column {
                            Text("Para eliminar tu cuenta, introduce tu correo electrónico:")
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = confirmationEmail,
                                onValueChange = {
                                    confirmationEmail = it
                                    emailError = false
                                },
                                label = { Text("Correo electrónico") },
                                isError = emailError,
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (emailError) {
                                Text(
                                    text = "El correo no coincide.",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            // Comprueba que el email introducido coincida con el actual antes de eliminar.
                            if (email != null) {
                                token?.let {
                                    userViewModel.deleteUser(context, it, email,
                                        onSuccess = {
                                            coroutineScope.launch {
                                                UserPreferences.clearUser(context)
                                                navController.navigate(AppScreen.CoverScreen.route) {
                                                    popUpTo(AppScreen.UserScreen.route) { inclusive = true }
                                                }

                                            }
                                        }, onError = { errorMsg ->
                                            emailError = true
                                        })
                                }
                            }
                        }) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

        }
    }
}