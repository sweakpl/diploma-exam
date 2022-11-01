package com.sweak.diplomaexam.data.remote.dto

import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.domain.model.common.UserRole

data class UserDto(
    val email: String,
    val role: String
)

fun UserDto.toUser(): User = User(UserRole.fromString(role), email)