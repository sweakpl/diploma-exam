package com.sweak.diplomaexam.data.local

interface UserSessionManager {
    fun saveSessionToken(sessionToken: String)
    fun saveSessionExpiryDate(expiryDateString: String)
    fun saveSessionId(sessionId: Int)
    fun saveUserRole(userRoleString: String)
    fun saveUserEmail(userEmail: String)
    fun getSessionToken(): String?
    fun getSessionTokenExpiryDate(): Long
    fun getSessionId(): Int
    fun getUserRole(): String?
    fun getUserEmail(): String?
    fun cleanUpSession()
}