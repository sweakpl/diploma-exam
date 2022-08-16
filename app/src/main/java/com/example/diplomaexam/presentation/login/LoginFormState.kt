package com.example.diplomaexam.presentation.login

import com.example.diplomaexam.common.UserRole
import com.example.diplomaexam.presentation.UiText

data class LoginFormState(
    val userRole: UserRole? = null,
    val loginHelpDialogVisible: Boolean = false,
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val errorMessage: UiText? = null,
    val isAuthorizing: Boolean = false
)
