package com.sweak.diplomaexam.data.remote.dto.session

data class SetSessionStatusRequestDto(
    val sessionId: Int,
    val state: String
)
