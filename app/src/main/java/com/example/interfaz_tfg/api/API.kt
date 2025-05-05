package com.example.interfaz_mesames.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Objeto que configura la comunicación con la API externa a través de Retrofit y OkHttpClient.
 *
 * Proporciona un servicio de Retrofit que puede ser utilizado para realizar peticiones a la API
 * y un cliente de OkHttp con configuraciones personalizadas para manejar los tiempos de espera
 * y la conexión.
 *
 * @property retrofitService Servicio de Retrofit que permite hacer peticiones a la API.
 * @property client Cliente de OkHttp que maneja la configuración de tiempo de espera y conexión.
 */
object API {
    /** URL base de la API */
    private const val BASE_URL = "https://api-tfg-uwn4.onrender.com"

    /**
     * Servicio de Retrofit para hacer las peticiones a la API.
     * Se crea de manera perezosa al acceder a este valor por primera vez.
     */
    val retrofitService: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }

    /**
     * Cliente de OkHttp con configuraciones de tiempo de espera.
     * Configura tiempos de espera para la conexión, lectura y escritura.
     */
    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Tiempo de espera al conectar
        .readTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para leer la respuesta
        .writeTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para escribir la petición
        .build()


    /**
     * Crea una instancia de Retrofit configurada con la URL base, el convertidor de Gson y el cliente de OkHttp.
     *
     * @return Una instancia de Retrofit configurada.
     */
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}