package com.example.egzamindyplomowy.presentation.introduction.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.domain.use_case.login.ValidateEmail
import com.example.egzamindyplomowy.domain.use_case.login.AuthenticateUser
import com.example.egzamindyplomowy.presentation.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val authenticateUser: AuthenticateUser = AuthenticateUser()
) : ViewModel() {

    var state by mutableStateOf(LoginFormState())

    private val authenticateEventChannel = Channel<AuthenticationEvent>()
    val authenticateEvents = authenticateEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
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