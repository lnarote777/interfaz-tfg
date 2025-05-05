package com.example.interfaz_mesames.api

import com.example.interfaz_mesames.api.model.LogInResponse
import com.example.interfaz_mesames.api.model.UserDTO
import com.example.interfaz_mesames.api.model.UserLoginDTO
import com.example.interfaz_mesames.api.model.UserRegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz que define los endpoints disponibles para interactuar con la API de tareas.
 *
 * Proporciona métodos para gestionar usuarios (registro, login) y tareas (crear, listar, actualizar y eliminar).
 */
interface ApiService {
    /**
     * Registra un nuevo usuario en la aplicación.
     *
     * @param usuarioInsertDTO Datos del usuario a registrar.
     * @return Respuesta con los datos del usuario registrado.
     */
    @POST("/users/register")
    suspend fun insertUser(@Body usuarioInsertDTO: UserRegisterDTO): Response<UserDTO>

    /**
     * Realiza el login de un usuario.
     *
     * @param usuarioLoginDTO Credenciales del usuario para iniciar sesión.
     * @return Respuesta con los datos de la sesión iniciada, incluyendo el token de autenticación.
     */
    @POST("/users/login")
    suspend fun login(@Body usuarioLoginDTO: UserLoginDTO): Response<LogInResponse>
}
