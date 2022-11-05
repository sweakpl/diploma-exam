package com.sweak.diplomaexam.data.remote.dto.login

data class LoginResponseDto(
    val email: String,
    val role: String,
    val token: String,
    val sessionId: Int?,
    val expirationDate: String
)
