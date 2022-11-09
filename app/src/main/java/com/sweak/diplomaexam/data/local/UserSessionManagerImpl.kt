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

    override fun saveSessionToken(sessionToken: String) =
        sharedPreferences.edit().putString(JWT_TOKEN_PREFERENCES_KEY, sessionToken).apply()

    override fun saveSessionExpiryDate(expiryDateString: String) =
        sharedPreferences.edit()
            .putLong(
                JWT_TOKEN_EXPIRY_DATE_PREFERENCES_KEY,
                getExpiryDateTimeInMillis(expiryDateString) ?: 0
            )
            .apply()

    override fun saveSessionId(sessionId: Int) =
        sharedPreferences.edit().putInt(SESSION_ID_PREFERENCES_KEY, sessionId).apply()

    override fun saveUserRole(userRoleString: String) {
        sharedPreferences.edit().putString(USER_ROLE_PREFERENCES_KEY, userRoleString).apply()
    }

    override fun saveUserEmail(userEmail: String) {
        sharedPreferences.edit().putString(USER_EMAIL_PREFERENCES_KEY, userEmail).apply()
    }

    private fun getExpiryDateTimeInMillis(expiryDateString: String): Long? {
        val simpleDateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSZZZ",
            Locale.getDefault()
        )

        return try {
            simpleDateFormat.parse(expiryDateString)?.time
        } catch (parseException: ParseException) {
            null
        }
    }

    override fun getSessionToken(): String? =
        sharedPreferences.getString(JWT_TOKEN_PREFERENCES_KEY, null)

    override fun getSessionTokenExpiryDate(): Long =
        sharedPreferences.getLong(JWT_TOKEN_EXPIRY_DATE_PREFERENCES_KEY, 0)

    override fun getSessionId(): Int =
        sharedPreferences.getInt(SESSION_ID_PREFERENCES_KEY, -1)

    override fun getUserRole() =
        sharedPreferences.getString(USER_ROLE_PREFERENCES_KEY, null)

    override fun getUserEmail() =
        sharedPreferences.getString(USER_EMAIL_PREFERENCES_KEY, null)

    override fun cleanUpSession() =
        sharedPreferences.edit()
            .putString(JWT_TOKEN_PREFERENCES_KEY, null)
            .putLong(JWT_TOKEN_EXPIRY_DATE_PREFERENCES_KEY, 0)
            .putInt(SESSION_ID_PREFERENCES_KEY, -1)
            .putString(USER_ROLE_PREFERENCES_KEY, null)
            .putString(USER_EMAIL_PREFERENCES_KEY, null)
            .apply()

    companion object {
        private const val JWT_TOKEN_FILE_NAME = "diploma-exam-jwt-token-file"
        private const val JWT_TOKEN_PREFERENCES_KEY = "jwtTokenPreferencesKey"
        private const val JWT_TOKEN_EXPIRY_DATE_PREFERENCES_KEY = "jwtTokenExpiryDatePreferencesKey"
        private const val SESSION_ID_PREFERENCES_KEY = "sessionIdPreferencesKey"
        private const val USER_ROLE_PREFERENCES_KEY = "userRolePreferencesKey"
        private const val USER_EMAIL_PREFERENCES_KEY = "userEmailPreferencesKey"
    }
}