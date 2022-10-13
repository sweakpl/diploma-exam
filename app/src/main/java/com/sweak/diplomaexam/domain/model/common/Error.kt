package com.sweak.diplomaexam.domain.model.common

sealed class Error {
    class HttpError(val code: Int, val message: String?) : Error()
    class IOError(val message: String?) : Error()
    class UnauthorizedError(val message: String?) : Error()
    object UnknownError : Error()
}
