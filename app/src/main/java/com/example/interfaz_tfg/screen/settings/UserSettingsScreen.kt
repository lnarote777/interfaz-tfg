package com.example.interfaz_tfg.screen.settings

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.UserPreferences
import com.example.interfaz_tfg.api.model.user.UserUpdateDTO
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.configuraciones.SettingItem
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.UserViewModel
import com.example.interfaz_tfg.viewModel.getRolFromToken


@SuppressLint("ContextCastToActivity")
@Composable
fun UserSettingsScreen(navController: NavController, username: String? ="", email: String? = ""){

    val userViewModel: UserViewModel = viewModel()
    val user = userViewModel.user.collectAsState()
    val selectedAvatarRes by userViewModel.selectedAvatarRes.collectAsState()
    val color = MaterialTheme.colorScheme
    var newUsername by remember { mutableStateOf(username ?: "") }
    var newPass by remember { mutableStateOf("") }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var isUsername by rememberSaveable { mutableStateOf(true) }
    var cambio by rememberSaveable { mutableStateOf(false) }
    var cambioPass by rememberSaveable { mutableStateOf(false) }
    var showImageChoiceDialog by remember { mutableStateOf(false) }
    var showAvatarDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var token = ""
    val rol = getRolFromToken(token)

    LaunchedEffect(Unit) {
        if (username != null) {
            userViewModel.getUserByUsername(username)
        }
        token = UserPreferences.getToken(context).toString()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        userViewModel.setSelectedImage(context, uri)
    }


    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { if (isUsername ) Text("Editar nombre Useario") else Text("Editar contraseña") },
            text = {
                Column {
                    if (isUsername) {
                        Text("Introduzca su nuevo nombre de usuario:")
                    }else{
                        Text("Introduzca la nueva contraseña:")
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = if (isUsername) newUsername else newPass,
                        onValueChange = {
                            if (isUsername) newUsername = it else newPass = it
                        },
                        label = { if(isUsername) Text("Nuevo nombre de usaurio") else Text("Nueva contraseña") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (isUsername){
                        cambio = true
                    }else{
                        cambioPass = true
                    }
                    showDialog = false
                }) {
                    Text("Cambiar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showImageChoiceDialog) {
        AlertDialog(
            onDismissRequest = { showImageChoiceDialog = false },
            title = { Text("Seleccionar imagen de perfil") },
            text = {
                Column {
                    if (rol == "PREMIUM"){
                        Text(
                            "Desde galería",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    launcher.launch("image/*")
                                    showImageChoiceDialog = false
                                }
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Text(
                        "Elegir avatar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageChoiceDialog = false
                                showAvatarDialog = true
                            }
                            .padding(8.dp)
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showImageChoiceDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showAvatarDialog) {
        AlertDialog(
            onDismissRequest = { showAvatarDialog = false },
            title = { Text("Selecciona un avatar") },
            text = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5).forEach { resId ->
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .clickable {
                                    userViewModel.setSelectedAvatar(context, resId)
                                    showAvatarDialog = false
                                }
                                .border(
                                    width = if (selectedAvatarRes == resId) 3.dp else 0.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAvatarDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold() { innerpadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .background(color = color.background)
        ) {
            Header(
                navController = navController,
                title = "Ajustes del perfil",
                back = false,
                route = AppScreen.UserScreen.route + "/$username/$email"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .padding(top = 20.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(color = Color.White)

            ){
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    SettingItem(
                        title = "Cambiar imagen de perfil",
                        isClickable = true,
                        onClick = { showImageChoiceDialog = true }
                    )

                    Divider(modifier = Modifier.padding(horizontal = 16.dp))

                    SettingItem(
                        title = "Cambiar nombre de usuario",
                        value = if (cambio) newUsername else username,
                        isClickable = true,
                        onClick = {showDialog = true ; isUsername = true}
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingItem(
                        title = "Cambiar contraseña",
                        value = if (cambioPass) "•".repeat(newPass.length) else "•".repeat(user.value?.name?.length ?: 8),
                        isClickable = true,
                        onClick = {showDialog = true ; isUsername = false}
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón para guardar cambios
                    Button(
                        onClick = {
                            val userUpdated = email?.let {
                                (if (cambio) newUsername else username)?.let { it1 ->
                                    UserUpdateDTO(
                                        email = it,
                                        username = it1,
                                        password =  if (cambioPass) newPass else ""
                                    )
                                }
                            }
                            if (userUpdated != null) {
                                userViewModel.updateUser(userUpdated, navController)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text("Guardar cambios")
                    }
                }
            }
        }
    }
}