package com.sweak.diplomaexam.presentation.screens.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.presentation.Screen
import com.sweak.diplomaexam.presentation.screens.common.WindowInfo
import com.sweak.diplomaexam.presentation.screens.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.screens.components.Dialog
import com.sweak.diplomaexam.presentation.screens.components.WelcomeLayout
import com.sweak.diplomaexam.presentation.screens.login.components.LoginForm
import com.sweak.diplomaexam.presentation.screens.login.components.UserRoleChoiceButtons
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        loginViewModel.authenticateEvents.collect { event ->
            when (event) {
                is LoginViewModel.AuthenticationEvent.Success -> {
                    navController.navigate(Screen.LobbyScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    val loginScreenState = loginViewModel.state
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactLoginScreen {
            AnimatedContent(
                targetState = loginScreenState.userRole,
                modifier = Modifier.padding(
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large,
                    bottom = MaterialTheme.space.large
                )
            ) { targetState ->
                if (targetState == null) {
                    UserRoleChoiceButtons(
                        onUserRoleChosen = {
                            loginViewModel.onEvent(LoginScreenEvent.UserRoleChosen(it))
                        }
                    )
                } else {
                    LoginForm(
                        emailAddress = loginScreenState.email,
                        onEmailAddressChange = {
                            loginViewModel.onEvent(LoginScreenEvent.EmailChanged(it))
                        },
                        password = loginScreenState.password,
                        onPasswordChange = {
                            loginViewModel.onEvent(LoginScreenEvent.PasswordChanged(it))
                        },
                        passwordVisible = loginScreenState.passwordVisible,
                        onPasswordVisibleClick = {
                            loginViewModel.onEvent(LoginScreenEvent.PasswordVisibilityChanged)
                        },
                        errorMessage = loginScreenState.errorMessage,
                        onLoginClick = {
                            loginViewModel.onEvent(LoginScreenEvent.Login)
                        },
                        isAuthorizing = loginScreenState.isAuthorizing,
                        onLoginHelpClick = {
                            loginViewModel.onEvent(LoginScreenEvent.LoginHelpVisible(true))
                        }
                    )
                }
            }
        }
    } else {
        MediumOrExpandedLoginScreen {
            AnimatedContent(
                targetState = loginScreenState.userRole,
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.space.large,
                    vertical = MaterialTheme.space.medium
                )
            ) { targetState ->
                if (targetState == null) {
                    UserRoleChoiceButtons(
                        onUserRoleChosen = {
                            loginViewModel.onEvent(LoginScreenEvent.UserRoleChosen(it))
                        }
                    )
                } else {
                    LoginForm(
                        emailAddress = loginScreenState.email,
                        onEmailAddressChange = {
                            loginViewModel.onEvent(LoginScreenEvent.EmailChanged(it))
                        },
                        password = loginScreenState.password,
                        onPasswordChange = {
                            loginViewModel.onEvent(LoginScreenEvent.PasswordChanged(it))
                        },
                        passwordVisible = loginScreenState.passwordVisible,
                        onPasswordVisibleClick = {
                            loginViewModel.onEvent(LoginScreenEvent.PasswordVisibilityChanged)
                        },
                        errorMessage = loginScreenState.errorMessage,
                        onLoginClick = {
                            loginViewModel.onEvent(LoginScreenEvent.Login)
                        },
                        isAuthorizing = loginScreenState.isAuthorizing,
                        onLoginHelpClick = {
                            loginViewModel.onEvent(LoginScreenEvent.LoginHelpVisible(true))
                        }
                    )
                }
            }
        }
    }

    if (loginScreenState.loginHelpDialogVisible) {
        Dialog(
            title = stringResource(R.string.login),
            message = stringResource(
                when (loginScreenState.userRole) {
                    UserRole.USER_STUDENT -> R.string.login_help_student_message
                    else -> R.string.login_help_examiner_message
                }
            ),
            onDismissRequest = {
                loginViewModel.onEvent(LoginScreenEvent.LoginHelpVisible(false))
            },
            onlyPositiveButton = true,
            onPositiveClick = {
                loginViewModel.onEvent(LoginScreenEvent.LoginHelpVisible(false))
            },
            positiveButtonText = stringResource(android.R.string.ok)
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun CompactLoginScreen(formComponent: @Composable () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        WelcomeLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large,
                    bottom = MaterialTheme.space.medium
                )
        )

        formComponent()
    }
}

@ExperimentalComposeUiApi
@Composable
fun MediumOrExpandedLoginScreen(formComponent: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            WelcomeLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large,
                        bottom = MaterialTheme.space.medium
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            formComponent()
        }
    }
}