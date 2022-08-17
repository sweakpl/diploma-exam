package com.sweak.diplomaexam.presentation.login

import com.sweak.diplomaexam.common.UserRole

sealed class LoginFormEvent {
    data class UserRoleChosen(val userRole: UserRole): LoginFormEvent()
    data class LoginHelpVisible(val isVisible: Boolean): LoginFormEvent()
    data class EmailChanged(val email: String): LoginFormEvent()
    data class PasswordChanged(val password: String): LoginFormEvent()
    object PasswordVisibilityChanged: LoginFormEvent()
    object Login : LoginFormEvent()
}
