package com.sweak.diplomaexam.domain.model

data class User(
    val role: UserRole,
    val email: String?
)
