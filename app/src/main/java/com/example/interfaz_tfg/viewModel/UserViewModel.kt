package com.example.interfaz_tfg.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfaz_tfg.utils.UserPreferences
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.api.model.user.UserUpdateDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {
    private val _users = MutableStateFlow<List<UserDTO>>(emptyList())
    val users: StateFlow<List<UserDTO>> = _users

    private val _user = MutableStateFlow<UserDTO?>(null)
    val user: StateFlow<UserDTO?> = _user

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    private val _selectedAvatarRes = MutableStateFlow<Int?>(null)
    val selectedAvatarRes: StateFlow<Int?> = _selectedAvatarRes

    fun initFromPreferences(context: Context) {
        viewModelScope.launch {
            runCatching {
                val uriStr = UserPreferences.getImageUri(context).firstOrNull()
                val avatarRes = UserPreferences.getAvatarRes(context).firstOrNull()
                _selectedImageUri.value = uriStr?.let { Uri.parse(it) }
                _selectedAvatarRes.value = avatarRes
                Log.d("UserViewModel", "Loaded uri: $uriStr, avatar: $avatarRes")
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun setSelectedAvatar(context: Context, resId: Int?) {
        _selectedAvatarRes.value = resId
        _selectedImageUri.value = null
        viewModelScope.launch {
            UserPreferences.saveAvatarRes(context, resId)
        }
    }

    fun setSelectedImage(context: Context, uri: Uri?) {
        _selectedImageUri.value = uri
        _selectedAvatarRes.value = null
        viewModelScope.launch {
            UserPreferences.saveImageUri(context, uri.toString())
        }
    }

    fun getUserByUsername(token: String, username: String){
        viewModelScope.launch {
            try {
                val response = API.retrofitService.getUserByUsername("Bearer $token", username)
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


    fun updateUser(token: String, user: UserUpdateDTO){
        viewModelScope.launch {
            try {
                val response = API.retrofitService.update("Bearer $token", user)
                if(response.isSuccessful){
                    Log.d("API", "Usuario actualizado")
                }else{
                    Log.e("API", "Error al actualizar usuario: ${response.code()}")
                }
            }catch (e: Exception){
                Log.e("API", "Excepción al actualizar usuario", e)
            }
        }
    }

    fun deleteUser(context: Context, token: String, email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {

                val response = API.retrofitService.deleteUser("Bearer $token", email)
                if (response.isSuccessful) {
                    UserPreferences.clearToken(context)
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