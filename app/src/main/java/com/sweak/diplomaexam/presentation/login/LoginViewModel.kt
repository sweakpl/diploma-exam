package com.sweak.diplomaexam.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.DUMMY_OTHER_USER_EMAIL
import com.sweak.diplomaexam.domain.DUMMY_OTHER_USER_ROLE
import com.sweak.diplomaexam.domain.DUMMY_USER_EMAIL
import com.sweak.diplomaexam.domain.DUMMY_USER_ROLE
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.use_case.login.AuthenticateUser
import com.sweak.diplomaexam.domain.use_case.login.ValidateEmail
import com.sweak.diplomaexam.presentation.common.UiText
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
        if (!validateEmail(state.email)) {
            state = state.copy(errorMessage = UiText.StringResource(R.string.invalid_email_error))
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
                        errorMessage =
                        if (it.data?.successful == true) null
                        else UiText.StringResource(R.string.unknown_error),
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
                is Resource.Failure -> state = state.copy(
                    errorMessage = when (it.data?.error) {
                        is Error.IOError -> UiText.StringResource(R.string.cant_reach_server)
                        is Error.HttpError -> {
                            if (it.data.error.message != null)
                                UiText.DynamicString(it.data.error.message)
                            else
                                UiText.StringResource(R.string.unknown_error)
                        }
                        is Error.UnauthorizedError ->
                            UiText.StringResource(R.string.wrong_credentials_error)
                        else -> UiText.StringResource(R.string.unknown_error)
                    },
                    isAuthorizing = false
                )
            }
        }.launchIn(viewModelScope)
    }

    sealed class AuthenticationEvent {
        object Success : AuthenticationEvent()
    }
}