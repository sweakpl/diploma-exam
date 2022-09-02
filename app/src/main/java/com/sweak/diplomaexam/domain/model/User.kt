package com.sweak.diplomaexam.domain.model

import com.sweak.diplomaexam.common.UserRole

data class User(
    val role: UserRole,
    val email: String?
)
