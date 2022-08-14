package com.example.egzamindyplomowy.presentation.login

import com.example.egzamindyplomowy.common.UserRole
import com.example.egzamindyplomowy.presentation.UiText

data class LoginFormState(
    val userRole: UserRole? = null,
    val loginHelpDialogVisible: Boolean = false,
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val errorMessage: UiText? = null,
    val isAuthorizing: Boolean = false
)
