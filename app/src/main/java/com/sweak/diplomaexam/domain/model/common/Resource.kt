package com.sweak.diplomaexam.domain.model.common

sealed class Resource<T>(val data: T? = null, val error: Error? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Failure<T>(error: Error, data: T? = null) : Resource<T>(data, error)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}