package com.example.interfaz_tfg

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object UserPreferences {
    private val Context.dataStore by preferencesDataStore("user_prefs")

    val TOKEN_KEY = stringPreferencesKey("token")
    val IMAGE_URI = stringPreferencesKey("profile_image_uri")
    val AVATAR_RES = intPreferencesKey("avatar_res_id")

    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    fun getToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun clearToken(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    suspend fun saveImageUri(context: Context, uri: String?) {
        context.dataStore.edit { prefs ->
            if (uri != null) {
                prefs[IMAGE_URI] = uri
                prefs.remove(AVATAR_RES)
            } else {
                prefs.remove(IMAGE_URI)
            }
        }
    }

    suspend fun saveAvatarRes(context: Context, resId: Int?) {
        context.dataStore.edit { prefs ->
            if (resId != null) {
                prefs[AVATAR_RES] = resId
                prefs.remove(IMAGE_URI)
            } else {
                prefs.remove(AVATAR_RES)
            }
        }
    }

    fun getImageUri(context: Context): Flow<String?> = context.dataStore.data.map { it[IMAGE_URI] }
    fun getAvatarRes(context: Context): Flow<Int?> = context.dataStore.data.map { it[AVATAR_RES] }

    suspend fun clearProfileImage(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(AVATAR_RES)
            preferences.remove(IMAGE_URI)
        }
    }
}