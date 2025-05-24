package com.example.interfaz_tfg.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.user.Subscription
import com.example.interfaz_tfg.api.model.user.SubscriptionType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PagoViewModel : ViewModel() {
    private val _checkoutUrl = MutableStateFlow<String?>(null)
    val checkoutUrl: StateFlow<String?> = _checkoutUrl

    fun iniciarPago(email: String, type: SubscriptionType) {
        viewModelScope.launch {
            try {
                val response = API.retrofitService.createSubscription(
                    Subscription(email = email, type = type)
                )
                if (response.isSuccessful) {
                    _checkoutUrl.value = response.body()?.url
                } else {
                    Log.e("PagoViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Exception: ${e.message}")
            }
        }
    }
}