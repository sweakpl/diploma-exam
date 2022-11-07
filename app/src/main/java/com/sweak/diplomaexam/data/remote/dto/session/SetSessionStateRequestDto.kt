package com.sweak.diplomaexam.data.remote.dto.session

data class SetSessionStateRequestDto(
    val sessionId: Int,
    val state: String
)
