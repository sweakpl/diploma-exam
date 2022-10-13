package com.sweak.diplomaexam.data.local

interface UserSessionManager {
    fun saveSessionToken(sessionToken: String)
    fun getSessionToken(): String?
}