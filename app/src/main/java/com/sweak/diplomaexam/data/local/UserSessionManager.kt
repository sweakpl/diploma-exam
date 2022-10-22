package com.sweak.diplomaexam.data.local

interface UserSessionManager {
    fun saveSessionTokenAndExpiryDate(sessionToken: String, expiryDateString: String)
    fun getSessionToken(): String?
    fun getSessionTokenExpiryDate(): Long
}