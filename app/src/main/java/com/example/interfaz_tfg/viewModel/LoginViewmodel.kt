package com.example.interfaz_mesames.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.auth0.jwt.JWT.decode
import com.auth0.jwt.interfaces.DecodedJWT

import com.example.interfaz_mesames.api.API
import com.example.interfaz_mesames.api.model.UserLoginDTO
import com.example.interfaz_mesames.navigation.AppScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar la lógica de inicio de sesión del usuario.
 * Se encarga de realizar la solicitud de autenticación a través de la API y de manejar el estado de la interfaz de usuario.
 */
class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState

    /**
     * Realiza el inicio de sesión con el usuario y contraseña proporcionados.
     * Si el inicio de sesión es exitoso, guarda el token y navega a la pantalla principal.
     * En caso de error, actualiza el estado con el mensaje correspondiente.
     *
     * @param user El nombre de usuario para el inicio de sesión.
     * @param password La contraseña para el inicio de sesión.
     * @param navController El controlador de navegación que permite navegar a otras pantallas.
     */
    fun login(user: String, password: String, navController: NavController) {
        viewModelScope.launch {
            try {
                // Realiza la solicitud de registro a la API
                val response = API.retrofitService.login(UserLoginDTO(user, password))
                if (response.isSuccessful) {
                    response.body()?.token?.let { token ->
                        _uiState.value = token
                        val userRol = obtenerRolDesdeToken(token)
                        navController.navigate(route = AppScreen.HomeScreen.route + "/$user/$userRol/$token")
                    } ?: run { _uiState.value = "Token no recibido" }
                } else {
                    _uiState.value = "Credenciales incorrectas."
                }
            } catch (e: Exception) {
                _uiState.value = "Error de conexión: ${e.message}"
            }
        }
    }

    /**
     * Extrae el rol del usuario desde el token JWT.
     * Si el token contiene el rol "ROLE_ADMIN", se devuelve "ADMIN", de lo contrario "USER".
     * Si ocurre un error al decodificar el token, se devuelve "USER".
     *
     * @param token El token JWT del usuario.
     * @return El rol del usuario, que puede ser "ADMIN" o "USER".
     */
    fun obtenerRolDesdeToken(token: String): String? {
        return try {
            val decodedJWT: DecodedJWT = decode(token)
            val roles = decodedJWT.getClaim("roles").asString()
            roles?.let {
                if (it == "ROLE_ADMIN") "ADMIN" else "USER"
            } ?: "USER" //
        } catch (e: Exception) {
            "USER"
        }
    }
}