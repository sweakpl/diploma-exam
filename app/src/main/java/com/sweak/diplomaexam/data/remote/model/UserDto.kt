package com.sweak.diplomaexam.data.remote.model

data class UserDto(
    val email: String,
    val examinationSessionId: List<Int>,
    val id: Int,
    val role: String
)