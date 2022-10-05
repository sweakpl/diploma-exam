package com.sweak.diplomaexam.domain.model

sealed class Error {
    class HttpError(val code: Int, val message: String?) : Error()
    class IOError(val message: String?) : Error()
    object UnknownError : Error()
}
