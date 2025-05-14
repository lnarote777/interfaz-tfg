package com.example.interfaz_tfg.compose

import com.example.interfaz_tfg.api.model.cycle.MenstrualFlowLevel

data class EmojiItem(val emoji: String, val label: String)

val moodEmojis = mapOf(
    "Feliz" to EmojiItem("😊", "Feliz"),
    "Triste" to EmojiItem("😢", "Triste"),
    "Irritada" to EmojiItem("😠", "Irritada"),
    "Cansada" to EmojiItem("https://imgur.com/yu1er90", "Cansada"),
    "Enamorada" to EmojiItem("🥰", "Enamorada"),
    "Ansiosa" to EmojiItem("😰", "Ansiosa"),
    "Segura de sí" to EmojiItem("😎", "Segura de sí"),
    "Confundida" to EmojiItem("😵", "Confundida")
)

val symptomEmojis = mapOf(
    "Dolor de cabeza" to EmojiItem("🤕", "Dolor de cabeza"),
    "Náuseas" to EmojiItem("🤢", "Náuseas"),
    "Congestión" to EmojiItem("🤧", "Congestión"),
    "Dolor abdominal" to EmojiItem("😖", "Dolor abdominal"),
    "Cambios de humor" to EmojiItem("😡", "Cambios de humor"),
    "Escalofríos" to EmojiItem("🥶", "Escalofríos"),
    "Fiebre" to EmojiItem("🔥", "Fiebre")
)

val menstrualflow = mapOf(
    "light" to EmojiItem("🩸", "Flujo leve"),
    "heavy" to EmojiItem("https://imgur.com/Zu9EVrt", "Flujo abundante"),
    "moderate" to EmojiItem("https://imgur.com/b9Vs37q", "Manchado"),
    "clots" to EmojiItem("https://imgur.com/WN4e1QP", "Coágulos")
)

val vaginalDischarge = mapOf(
    "transparente y elástico" to EmojiItem("https://imgur.com/ZapUivZ", "Transparente y elástico"),
    "blanco" to EmojiItem("https://imgur.com/lbz6KE6", "Blanco"),
    "espeso" to EmojiItem("🚫", "espeso"),
    "cremoso" to EmojiItem("https://imgur.com/5TLy6Bi", "cremoso"),
    "acuoso" to EmojiItem("https://imgur.com/C0fvGeh", "acuoso"),
    "amarillo" to EmojiItem("https://imgur.com/OocWcoN", "amarillo verdoso"),
    "grisáceo" to EmojiItem("https://imgur.com/YPYV4uz", "grisáceo"),
    "olor fuerte" to EmojiItem("https://imgur.com/Y3WjPaJ", "olor fuerte"),
    "grumoso" to EmojiItem("🚫", "grumoso"),
    "marron" to EmojiItem("🚫", "marrón o con sangre"),
    "sin flujo" to EmojiItem("🚫", "sin flujo")
)

val sexEmojis = mapOf(
    "Sí" to EmojiItem("❤️", "Sí"),
    "No" to EmojiItem("🚫", "No")
)

val physicalActivityEmojis = mapOf(
    "Correr" to EmojiItem("🏃‍♀️", "Correr"),
    "Yoga" to EmojiItem("🧘", "Yoga"),
    "Gimnasio" to EmojiItem("🏋️", "Gimnasio"),
    "Caminar" to EmojiItem("🚶", "Caminar"),
    "Reposo" to EmojiItem("🛌", "Reposo")
)

val waterEmojis = mapOf(
    "Poco" to EmojiItem("💧", "Poco"),
    "Medio" to EmojiItem("💧💧", "Medio"),
    "Mucho" to EmojiItem("💧💧💧", "Mucho")
)

val pillEmojis = mapOf(
    "Sí" to EmojiItem("💊", "Sí"),
    "No" to EmojiItem("🚫", "No")
)