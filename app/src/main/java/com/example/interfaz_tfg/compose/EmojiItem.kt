package com.example.interfaz_tfg.compose

import com.example.interfaz_tfg.api.model.cycle.MenstrualFlowLevel

data class EmojiItem(val emoji: String, val label: String)

val moodEmojis = mapOf(
    "Feliz" to EmojiItem("😊", "Feliz"),
    "Triste" to EmojiItem("😢", "Triste"),
    "Irritada" to EmojiItem("😠", "Irritada"),
    "Cansada" to EmojiItem("😩", "Cansada"),
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
    "heavy" to EmojiItem("💉", "Flujo abundante"),
    "moderate" to EmojiItem("🔴", "Manchado"),
    "clots" to EmojiItem("🧬", "Coágulos")
)

val vaginalDischarge = mapOf(
    "transparente y elástico" to EmojiItem("💧", "Transparente y elástico"),
    "blanco" to EmojiItem("⚪", "Blanco"),
    "espeso" to EmojiItem("🧊", "Espeso"),
    "cremoso" to EmojiItem("🍦", "Cremoso"),
    "acuoso" to EmojiItem("💦", "Acuoso"),
    "amarillo" to EmojiItem("🟡", "Amarillo verdoso"),
    "grisáceo" to EmojiItem("🌫️", "Grisáceo"),
    "olor fuerte" to EmojiItem("👃💨", "Olor fuerte"),
    "grumoso" to EmojiItem("🥣", "Grumoso"),
    "marron" to EmojiItem("🟤", "Marrón o con sangre"),
    "sin flujo" to EmojiItem("🚫", "Sin flujo")
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