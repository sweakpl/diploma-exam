package com.sweak.diplomaexam.domain.model.login

data class LoginResponse(
    val email: String,
    val role: String,
    val token: String,
    val expirationDate: String
)
