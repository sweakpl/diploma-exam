package com.sweak.diplomaexam.data.remote.common

enum class ResponseCode(val codeInt: Int) {
    OK(200),
    UNAUTHORIZED(401),
    INTERNAL_SERVER_ERROR(500)
}