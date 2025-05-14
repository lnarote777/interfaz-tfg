package com.example.interfaz_tfg.api

import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.cycle.DailyLogDTO
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycleDTO
import com.example.interfaz_tfg.api.model.user.LogInResponse
import com.example.interfaz_tfg.api.model.user.Subscription
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.api.model.user.UserLoginDTO
import com.example.interfaz_tfg.api.model.user.UserRegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.time.LocalDate

/**
 * Interfaz que define los endpoints disponibles para interactuar con la API de tareas.
 *
 * Proporciona métodos para gestionar usuarios (registro, login) y tareas (crear, listar, actualizar y eliminar).
 */
interface ApiService {
    //------------Usuarios---------------
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

    @GET("/users/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): Response<UserDTO>

    @PUT("/users/update")
    suspend fun update(): Response<UserDTO>

    @DELETE("/user/delete/{email}")
    suspend fun deleteByEmail(
        @Header("Authorization") token: String,
        @Path("email") email: String
    ): Response<UserDTO>

    //------------Pagos---------------
    @POST("/pay/create-subscription")
    suspend fun createSubscription(
        @Header("Authorization") token: String,
        @Body request: Subscription
    ): Response<String>

    //------------Log Diarios---------------
    @POST("/daily-log/new/{email}")
    suspend fun createLog(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Body log: DailyLogDTO
    ): Response<DailyLog>

    @GET("/daily-log/user/{email}/date/{date}")
    suspend fun getLogByDate(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("date") date: LocalDate
    ): Response<DailyLog>

    @PUT("daily-log//user/{userId}/date/{date}")
    suspend fun updateLog(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("date") date: LocalDate,
        @Body log: DailyLogDTO
    ): Response<DailyLog>

    //------------Cyclo Menstrual---------------
    @PUT("/cycles/new")
    suspend fun createCycle(
        @Body cycle: MenstrualCycleDTO
    )

    @GET("/cycles/user/{email}/prediction")
    suspend fun getPrediction(
        @Header("Authorization") token: String,
        @Path("email") email: String
    )
}
