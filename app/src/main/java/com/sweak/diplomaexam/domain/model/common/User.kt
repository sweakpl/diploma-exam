package com.sweak.diplomaexam.domain.model.common

data class User(
    val role: UserRole,
    val email: String?
)
