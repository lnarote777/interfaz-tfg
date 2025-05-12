package com.example.interfaz_tfg.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.user.UserDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val _users = MutableStateFlow<List<UserDTO>>(emptyList())
    val users: StateFlow<List<UserDTO>> = _users

    private val _user = MutableStateFlow<UserDTO?>(null)
    val user: StateFlow<UserDTO?> = _user

    fun getUserByUsername(username: String){
        viewModelScope.launch {
            val response = API.retrofitService.getUserByUsername(username)
            if (response.isSuccessful) {
                _user.value = response.body()!!
                Log.d("TAG", "Lista ")
            }else{
                Log.d("Error", "Error al obtener usuario ")
            }
        }
    }
}