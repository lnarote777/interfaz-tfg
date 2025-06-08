package com.example.interfaz_tfg.compose.profile

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.ProfileImage
import com.example.interfaz_tfg.compose.configuraciones.SettingCard
import com.example.interfaz_tfg.compose.profile.profileDataClasses.UserInfo
import com.example.interfaz_tfg.viewModel.UserViewModel

@Composable
fun UserProfileSection(
    userInfo: UserInfo,
    selectedImageUri: Uri?,
    userViewModel: UserViewModel,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingCard(height = 250.dp) {
            userInfo.username?.let { username ->
                userInfo.email?.let { email ->
                    ProfileImage(
                        uri = selectedImageUri,
                        avatarRes = userViewModel.selectedAvatarRes.collectAsState().value,
                        navController = navController,
                        username = username,
                        email = email
                    )
                    Text(
                        text = username,
                        fontSize = 25.sp,
                        color = Color.Black
                    )
                    Text(
                        text = email,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
