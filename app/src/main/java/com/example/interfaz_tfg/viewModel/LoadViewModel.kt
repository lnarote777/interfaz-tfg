package com.example.interfaz_tfg.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interfaz_tfg.UserPreferences
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.user.UserLoginDTO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoadViewModel(application: Application) : AndroidViewModel(application) {

    fun  checkLogin(
        onResult: (navigateTo: String) -> Unit
    ) {
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            val token = UserPreferences.getToken(context).first()
            if (!token.isNullOrEmpty()) {
                val username = getUsernameFromToken(token)
                val rol = getRolFromToken(token)
                val destination = "home/$username/$rol/$token"
                onResult(destination)
            } else {
                onResult("cover")
            }
        }
    }
}
