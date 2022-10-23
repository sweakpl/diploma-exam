package com.sweak.diplomaexam.domain.model.common

enum class UserRole(val apiName: String) {
    USER_STUDENT("STUDENT"), USER_EXAMINER("EXAMINER");

    override fun toString(): String {
        return apiName
    }

    companion object {
        fun fromString(name: String) = values().firstOrNull { it.apiName == name }
    }
}