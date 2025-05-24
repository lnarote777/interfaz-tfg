package com.example.interfaz_tfg.api.model.user

data class Subscription(
    val email: String,
    val type: SubscriptionType
)

enum class SubscriptionType {
    MONTHLY, ONE_TIME
}