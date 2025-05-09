package com.example.interfaz_tfg.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.UserRegisterDTO
import com.example.interfaz_tfg.navigation.AppScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar el registro de un nuevo usuario.
 * Se encarga de realizar la solicitud de registro a la API y manejar el estado de la interfaz de usuario.
 */
class RegistrerViewModel: ViewModel() {
    private val _uiState = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState

    /**
     * Registra un nuevo usuario en la aplicación.
     * Realiza la solicitud a la API con los datos del usuario y maneja el resultado del registro.
     * Si el registro es exitoso, navega a la pantalla de inicio de sesión. Si hay errores, actualiza el estado con el mensaje correspondiente.
     *
     * @param name El nombre completo del usuario.
     * @param username El nombre de usuario.
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña proporcionada por el usuario.
     * @param passRepeat La confirmación de la contraseña.
     * @param navController El controlador de navegación que permite navegar a otras pantallas.
     */
    fun registerUser(
        name: String,
        username:String,
        email: String,
        pass: String,
        passRepeat: String,
        birthdate: String,
        navController: NavController,
    ){

        val registerRequest = UserRegisterDTO(
            username = username,
            name = name,
            email = email,
            password = pass,
            passwordRepeat = passRepeat,
            birthDate = birthdate
        )

        viewModelScope.launch {
            try {
                // Realiza la solicitud de registro a la API
                val response = API.retrofitService.insertUser(registerRequest)

                if (response.isSuccessful) {
                    val usuarioDTO = response.body()
                    if (usuarioDTO != null) {
                        _uiState.value = "Registro exitoso."
                        navController.navigate(route = AppScreen.LoginScreen.route)
                    } else {
                        _uiState.value = "Error: No se recibió un usuario válido"
                    }
                } else {
                    _uiState.value = "Error al registrar usuario"
                }
            } catch (e: Exception) {
                _uiState.value = "Error de conexión: ${e.message}"
            }
        }

    }
}