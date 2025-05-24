package com.example.interfaz_tfg.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.api.model.user.UserUpdateDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val _users = MutableStateFlow<List<UserDTO>>(emptyList())
    val users: StateFlow<List<UserDTO>> = _users

    private val _user = MutableStateFlow<UserDTO?>(null)
    val user: StateFlow<UserDTO?> = _user

    val selectedImageUri = MutableStateFlow<Uri?>(null)

    val selectedImageName = MutableStateFlow<String?>(null)

    fun setSelectedImageName(name: String?) {
        selectedImageName.value = name
    }

    fun setSelectedImage(uri: Uri?) {
        selectedImageUri.value = uri
    }

    fun getUserByUsername(username: String){
        viewModelScope.launch {
            try {
                val response = API.retrofitService.getUserByUsername(username)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _user.value = it
                        Log.d("TAG", "Usuario cargado correctamente")
                    } ?: Log.e("API", "Body vacío en respuesta exitosa")
                } else {
                    Log.e("API", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Excepción en getUserByUsername", e)
            }
        }
    }

    fun updateUser(user: UserUpdateDTO, navController: NavController){
        viewModelScope.launch {
            try {
                val response = API.retrofitService.update(user)
                if(response.isSuccessful){
                    navController.popBackStack()
                    Log.d("API", "Usuario actualizado")
                }else{
                    Log.e("API", "Error al actualizar usuario: ${response.code()}")
                }
            }catch (e: Exception){
                Log.e("API", "Excepción al actualizar usuario", e)
            }
        }
    }

    fun deleteUser(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {

                val response = API.retrofitService.deleteUser(email)
                if (response.isSuccessful) {
                    onSuccess()
                    Log.d("API", "Usuario eliminado correctamente")
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error desconocido"
                    onError(errorMsg)
                    Log.e("API", "Error al eliminar usuario: ${response.code()} - $errorMsg")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Excepción al eliminar usuario")
                Log.e("API", "Excepción al eliminar usuario", e)
            }
        }
    }

}