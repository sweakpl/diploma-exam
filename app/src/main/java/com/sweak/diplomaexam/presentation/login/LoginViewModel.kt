package com.sweak.diplomaexam.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.common.*
import com.sweak.diplomaexam.domain.use_case.login.AuthenticateUser
import com.sweak.diplomaexam.domain.use_case.login.ValidateEmail
import com.sweak.diplomaexam.presentation.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val authenticateUser: AuthenticateUser
) : ViewModel() {

    var state by mutableStateOf(LoginScreenState())

    private val authenticateEventChannel = Channel<AuthenticationEvent>()
    val authenticateEvents = authenticateEventChannel.receiveAsFlow()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.UserRoleChosen ->
                state = state.copy(userRole = event.userRole)
            is LoginScreenEvent.EmailChanged ->
                state = state.copy(email = event.email, errorMessage = null)
            is LoginScreenEvent.PasswordChanged ->
                state = state.copy(password = event.password, errorMessage = null)
            is LoginScreenEvent.PasswordVisibilityChanged ->
                state = state.copy(passwordVisible = !state.passwordVisible)
            is LoginScreenEvent.LoginHelpVisible ->
                state = state.copy(loginHelpDialogVisible = event.isVisible)
            is LoginScreenEvent.Login -> {
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

        authenticateUser(state.email, state.password).onEach {
            when (it) {
                is Resource.Loading -> state = state.copy(isAuthorizing = true)
                is Resource.Success -> {
                    state = state.copy(
                        errorMessage = it.data?.errorMessage,
                        isAuthorizing = false
                    )

                    if (it.data?.successful == true) {
                        DUMMY_USER_ROLE = state.userRole ?: UserRole.USER_STUDENT
                        DUMMY_USER_EMAIL = state.email

                        if (DUMMY_USER_ROLE == UserRole.USER_STUDENT) {
                            DUMMY_OTHER_USER_ROLE = UserRole.USER_EXAMINER
                            DUMMY_OTHER_USER_EMAIL = "barbara.nowak@pk.edu.pl"
                        } else {
                            DUMMY_OTHER_USER_ROLE = UserRole.USER_STUDENT
                            DUMMY_OTHER_USER_EMAIL = "adam.kowalski@student.pk.edu.pl"
                        }

                        authenticateEventChannel.send(AuthenticationEvent.Success)
                    }
                }
                is Resource.Error -> state = state.copy(
                    errorMessage = it.data?.errorMessage,
                    isAuthorizing = false
                )
            }
        }.launchIn(viewModelScope)
    }

    sealed class AuthenticationEvent {
        object Success : AuthenticationEvent()
    }
}