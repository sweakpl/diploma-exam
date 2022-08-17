package com.sweak.diplomaexam.presentation.login

import com.sweak.diplomaexam.common.UserRole
import com.sweak.diplomaexam.presentation.ui.util.UiText

data class LoginFormState(
    val userRole: UserRole? = null,
    val loginHelpDialogVisible: Boolean = false,
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val errorMessage: UiText? = null,
    val isAuthorizing: Boolean = false
)
