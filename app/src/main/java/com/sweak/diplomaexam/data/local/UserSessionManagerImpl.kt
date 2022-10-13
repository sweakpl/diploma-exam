package com.sweak.diplomaexam.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class UserSessionManagerImpl(context: Context) : UserSessionManager {

    private val sharedPreferences: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            JWT_TOKEN_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveSessionToken(sessionToken: String) =
        sharedPreferences.edit().putString(JWT_TOKEN_PREFERENCES_KEY, sessionToken).apply()

    override fun getSessionToken(): String? =
        sharedPreferences.getString(JWT_TOKEN_PREFERENCES_KEY, null)

    companion object {
        private const val JWT_TOKEN_FILE_NAME = "diploma-exam-jwt-token-file"
        private const val JWT_TOKEN_PREFERENCES_KEY = "jwtTokenPreferencesKey"
    }
}