package com.example.egzamindyplomowy.presentation.introduction.login

import com.example.egzamindyplomowy.presentation.UiText

data class LoginFormState(
    val loginHelpDialogVisible: Boolean = false,
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val errorMessage: UiText? = null,
    val isAuthorizing: Boolean = false
)
