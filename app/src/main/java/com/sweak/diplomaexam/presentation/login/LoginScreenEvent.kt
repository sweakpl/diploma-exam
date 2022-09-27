package com.sweak.diplomaexam.presentation.login

import com.sweak.diplomaexam.domain.model.UserRole

sealed class LoginScreenEvent {
    data class UserRoleChosen(val userRole: UserRole): LoginScreenEvent()
    data class LoginHelpVisible(val isVisible: Boolean): LoginScreenEvent()
    data class EmailChanged(val email: String): LoginScreenEvent()
    data class PasswordChanged(val password: String): LoginScreenEvent()
    object PasswordVisibilityChanged: LoginScreenEvent()
    object Login : LoginScreenEvent()
}
