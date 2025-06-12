package com.example.interfaz_tfg.api

import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.cycle.DailyLogDTO
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycle
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycleDTO
import com.example.interfaz_tfg.api.model.user.LogInResponse
import com.example.interfaz_tfg.api.model.user.Subscription
import com.example.interfaz_tfg.api.model.user.SubscriptionResponse
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.api.model.user.UserLoginDTO
import com.example.interfaz_tfg.api.model.user.UserRegisterDTO
import com.example.interfaz_tfg.api.model.user.UserUpdateDTO
import retrofit2.Response
import retrofit2.http.*
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
    suspend fun getUserByUsername(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<UserDTO>

    @GET("/users/{userId}")
    suspend fun getUserByEmail(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Response<UserDTO>

    @PUT("/users/update")
    suspend fun update(
        @Header("Authorization") token: String,
        @Body user: UserUpdateDTO
    ): Response<UserDTO>

    @DELETE("/users/delete")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Query("email") email: String
    ): Response<UserDTO>

    //------------Pagos---------------
    @POST("/pay/create-subscription")
    suspend fun createSubscription(
        @Header("Authorization") token: String,
        @Body request: Subscription
    ): Response<SubscriptionResponse>

    //------------Log Diarios---------------
    @POST("/daily-log/new/{email}")
    suspend fun createLog(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Body log: DailyLogDTO
    ): Response<DailyLog>

    @GET("/daily-log/user/{userId}/date/{date}")
    suspend fun getLogByDate(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Path("date") date: String
    ): Response<DailyLog?>

    @GET("/daily-log/user/{userId}")
    suspend fun getLogsByUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Response<List<DailyLog>>

    @PUT("daily-log/user/{userId}/date/{date}")
    suspend fun updateLog(
        @Header("Authorization") token: String,
        @Path("userId") email: String,
        @Path("date") date: String,
        @Body log: DailyLogDTO
    ): Response<DailyLog>

    //------------Cyclo Menstrual---------------
    @POST("/cycles/new")
    suspend fun createCycle(
        @Body cycle: MenstrualCycleDTO
    ): Response<MenstrualCycle>

    @GET("/cycles/user/{email}")
    suspend fun getCyclesUser(
        @Path("email") email: String
    ): Response<List<MenstrualCycle>>

    @GET("/cycles/recalculate/{userId}")
    suspend fun recalculateCycle(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("date") date: String? = null
    ): Response<MenstrualCycle>

    @GET("/cycles/user/{email}/prediction")
    suspend fun getPrediction(
        @Path("email") email: String
    ):Response<MutableList<MenstrualCycle?>>

    @PUT("/cycles/update")
    suspend fun updateCycle(
        @Header("Authorization") token: String,
        @Body cycle: MenstrualCycle
    ): Response<MenstrualCycle>
}
