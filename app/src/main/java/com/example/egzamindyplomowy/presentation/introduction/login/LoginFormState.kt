package com.example.egzamindyplomowy.presentation.introduction.login

import com.example.egzamindyplomowy.presentation.UiText

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val errorMessage: UiText? = null,
    val isAuthorizing: Boolean = false
)
