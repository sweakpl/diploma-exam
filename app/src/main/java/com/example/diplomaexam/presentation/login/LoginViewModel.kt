package com.example.diplomaexam.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplomaexam.R
import com.example.diplomaexam.domain.use_case.login.ValidateEmail
import com.example.diplomaexam.domain.use_case.login.AuthenticateUser
import com.example.diplomaexam.presentation.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val authenticateUser: AuthenticateUser
) : ViewModel() {

    var state by mutableStateOf(LoginFormState())

    private val authenticateEventChannel = Channel<AuthenticationEvent>()
    val authenticateEvents = authenticateEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.UserRoleChosen ->
                state = state.copy(userRole = event.userRole)
            is LoginFormEvent.EmailChanged ->
                state = state.copy(email = event.email, errorMessage = null)
            is LoginFormEvent.PasswordChanged ->
                state = state.copy(password = event.password, errorMessage = null)
            is LoginFormEvent.PasswordVisibilityChanged ->
                state = state.copy(passwordVisible = !state.passwordVisible)
            is LoginFormEvent.LoginHelpVisible ->
                state = state.copy(loginHelpDialogVisible = event.isVisible)
            is LoginFormEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        val emailResult = validateEmail(state.email)

        if (!emailResult.successful) {
            state = state.copy(errorMessage = emailResult.errorMessage)
            return
        }

        if (state.password.isBlank()) {
            state = state.copy(errorMessage = UiText.StringResource(R.string.blank_password_error))
            return
        }

        state = state.copy(isAuthorizing = true)

        viewModelScope.launch {
            val authenticationResult = authenticateUser(state.email, state.password)

            state = state.copy(
                errorMessage = authenticationResult.errorMessage,
                isAuthorizing = false
            )

            if (authenticationResult.successful) {
                authenticateEventChannel.send(AuthenticationEvent.Success)
            }
        }
    }

    sealed class AuthenticationEvent {
        object Success : AuthenticationEvent()
    }
}