package com.sweak.diplomaexam.data.local

interface UserSessionManager {
    fun saveSessionToken(sessionToken: String)
    fun saveSessionExpiryDate(expiryDateString: String)
    fun saveSessionId(sessionId: Int)
    fun getSessionToken(): String?
    fun getSessionTokenExpiryDate(): Long
    fun getSessionId(): Int
    fun cleanUpSession()
}