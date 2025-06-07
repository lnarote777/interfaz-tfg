package com.example.interfaz_tfg.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.user.UserLoginDTO
import com.example.interfaz_tfg.utils.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.math.log

class LoadViewModel(application: Application) : AndroidViewModel(application) {

    fun  checkLogin(
        onResult: (navigateTo: String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val context = getApplication<Application>().applicationContext
                val username = UserPreferences.getUsername(context).firstOrNull()
                val password = UserPreferences.getPassword(context).firstOrNull()
                UserPreferences.clearToken(context)

                if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    val response = API.retrofitService.login(UserLoginDTO(username, password))

                    if (response.isSuccessful) {
                        response.body()?.let { loginResponse ->
                            val token = loginResponse.token
                            UserPreferences.saveToken(context, token)
                            val rol = getRolFromToken(token)
                            val destination = "home/$username/$rol/$token"
                            onResult(destination)
                        } ?: onResult("cover")
                    } else {
                        onResult("cover")
                    }
                } else {
                    onResult("cover")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult("cover")
            }
        }
    }
}
