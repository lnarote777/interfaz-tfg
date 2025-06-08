package com.example.interfaz_tfg.compose.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.profile.profileDataClasses.UserInfo
import com.example.interfaz_tfg.compose.profile.profileDataClasses.UserScreenState
import com.example.interfaz_tfg.viewModel.UserViewModel

@Composable
fun UserScreenUI(
    navController: NavController,
    userInfo: UserInfo,
    screenState: UserScreenState,
    selectedImageUri: Uri?,
    userViewModel: UserViewModel,
    onStateChange: (UserScreenState) -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            item {
                UserScreenHeader(
                    navController = navController,
                    token = screenState.token,
                    username = userInfo.username
                )
            }
            
            // Spacer superior
            item { Spacer(Modifier.height(10.dp)) }
            
            // Profile Card
            item {
                UserProfileSection(
                    userInfo = userInfo,
                    selectedImageUri = selectedImageUri,
                    userViewModel = userViewModel,
                    navController = navController
                )
            }
            
            // Cycle Info Card
            item {
                CycleInfoSection(
                    userInfo = userInfo,
                    navController = navController
                )
            }
            
            // Account Actions Card
            item {
                AccountActionsSection(
                    onLogout = onLogout,
                    onDeleteAccount = {
                        onStateChange(screenState.copy(showDeleteDialog = true))
                    }
                )
            }
        }
        
        // Delete Dialog
        if (screenState.showDeleteDialog) {
            DeleteAccountDialog(
                userInfo = userInfo,
                screenState = screenState,
                onStateChange = onStateChange,
                onConfirm = onDeleteAccount,
                onDismiss = {
                    onStateChange(
                        screenState.copy(
                            showDeleteDialog = false,
                            confirmationEmail = "",
                            emailError = false
                        )
                    )
                }
            )
        }
    }
}
