package com.sweak.diplomaexam.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

    override fun saveSessionTokenAndExpiryDate(sessionToken: String, expiryDateString: String) {
        val simpleDateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSZZZ",
            Locale.getDefault()
        )

        val expiryDateTimeInMillis = try {
            simpleDateFormat.parse(expiryDateString)?.time
        } catch (parseException: ParseException) {
            null
        }

        sharedPreferences.edit()
            .putString(JWT_TOKEN_PREFERENCES_KEY, sessionToken)
            .putLong(JWT_TOKEN_EXPIRY_DATE_PREFERENCES_KEY, expiryDateTimeInMillis ?: 0)
            .apply()
    }

    override fun getSessionToken(): String? =
        sharedPreferences.getString(JWT_TOKEN_PREFERENCES_KEY, null)

    override fun getSessionTokenExpiryDate(): Long =
        sharedPreferences.getLong(JWT_TOKEN_EXPIRY_DATE_PREFERENCES_KEY, 0)

    companion object {
        private const val JWT_TOKEN_FILE_NAME = "diploma-exam-jwt-token-file"
        private const val JWT_TOKEN_PREFERENCES_KEY = "jwtTokenPreferencesKey"
        private const val JWT_TOKEN_EXPIRY_DATE_PREFERENCES_KEY = "jwtTokenExpiryDatePreferencesKey"
    }
}