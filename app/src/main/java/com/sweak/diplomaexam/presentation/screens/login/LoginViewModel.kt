package com.sweak.diplomaexam.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.use_case.login.AuthenticateUser
import com.sweak.diplomaexam.domain.use_case.login.ValidateEmail
import com.sweak.diplomaexam.presentation.screens.common.UiText
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

        if (state.userRole == null) {
            state = state.copy(errorMessage = UiText.StringResource(R.string.unknown_error))
            return
        }

        authenticateUser(state.email, state.password, state.userRole!!).onEach {
            when (it) {
                is Resource.Loading -> state = state.copy(isAuthorizing = true)
                is Resource.Success -> {
                    state = state.copy(isAuthorizing = false)

                    authenticateEventChannel.send(AuthenticationEvent(state.userRole!!))
                }
                is Resource.Failure -> state = state.copy(
                    errorMessage = when (it.error) {
                        is Error.IOError -> UiText.StringResource(R.string.cant_reach_server)
                        is Error.HttpError -> {
                            if (it.error.message != null)
                                UiText.DynamicString(it.error.message)
                            else
                                UiText.StringResource(R.string.unknown_error)
                        }
                        is Error.UnauthorizedError ->
                            UiText.StringResource(R.string.wrong_credentials_error)
                        is Error.WrongUserRoleError ->
                            if (it.error.selectedUserRole == UserRole.USER_STUDENT)
                                UiText.StringResource(R.string.email_belongs_to_examiner)
                            else
                                UiText.StringResource(R.string.email_belongs_to_student)
                        else -> UiText.StringResource(R.string.unknown_error)
                    },
                    isAuthorizing = false
                )
            }
        }.launchIn(viewModelScope)
    }

    data class AuthenticationEvent(val authenticatedUserRole: UserRole)
}