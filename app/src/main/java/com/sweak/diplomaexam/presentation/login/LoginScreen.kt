package com.sweak.diplomaexam.presentation.login

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
import com.sweak.diplomaexam.common.UserRole
import com.sweak.diplomaexam.presentation.Screen
import com.sweak.diplomaexam.presentation.components.Dialog
import com.sweak.diplomaexam.presentation.components.WelcomeLayout
import com.sweak.diplomaexam.presentation.login.components.LoginForm
import com.sweak.diplomaexam.presentation.login.components.UserRoleChoiceButtons
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.ui.util.WindowInfo
import com.sweak.diplomaexam.presentation.ui.util.rememberWindowInfo

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

    val loginFormState = loginViewModel.state
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactLoginScreen {
            AnimatedContent(targetState = loginFormState.userRole) { targetState ->
                if (targetState == null) {
                    UserRoleChoiceButtons {
                        loginViewModel.onEvent(LoginFormEvent.UserRoleChosen(it))
                    }
                } else {
                    LoginForm(
                        emailAddress = loginFormState.email,
                        onEmailAddressChange = {
                            loginViewModel.onEvent(LoginFormEvent.EmailChanged(it))
                        },
                        password = loginFormState.password,
                        onPasswordChange = {
                            loginViewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                        },
                        passwordVisible = loginFormState.passwordVisible,
                        onPasswordVisibleClick = {
                            loginViewModel.onEvent(LoginFormEvent.PasswordVisibilityChanged)
                        },
                        errorMessage = loginFormState.errorMessage,
                        onLoginClick = {
                            loginViewModel.onEvent(LoginFormEvent.Login)
                        },
                        isAuthorizing = loginFormState.isAuthorizing,
                        onLoginHelpClick = {
                            loginViewModel.onEvent(LoginFormEvent.LoginHelpVisible(true))
                        }
                    )
                }
            }
        }
    } else {
        MediumOrExpandedLoginScreen {
            AnimatedContent(targetState = loginFormState.userRole) { targetState ->
                if (targetState == null) {
                    UserRoleChoiceButtons {
                        loginViewModel.onEvent(LoginFormEvent.UserRoleChosen(it))
                    }
                } else {
                    LoginForm(
                        emailAddress = loginFormState.email,
                        onEmailAddressChange = {
                            loginViewModel.onEvent(LoginFormEvent.EmailChanged(it))
                        },
                        password = loginFormState.password,
                        onPasswordChange = {
                            loginViewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                        },
                        passwordVisible = loginFormState.passwordVisible,
                        onPasswordVisibleClick = {
                            loginViewModel.onEvent(LoginFormEvent.PasswordVisibilityChanged)
                        },
                        errorMessage = loginFormState.errorMessage,
                        onLoginClick = {
                            loginViewModel.onEvent(LoginFormEvent.Login)
                        },
                        isAuthorizing = loginFormState.isAuthorizing,
                        onLoginHelpClick = {
                            loginViewModel.onEvent(LoginFormEvent.LoginHelpVisible(true))
                        }
                    )
                }
            }
        }
    }

    if (loginFormState.loginHelpDialogVisible) {
        Dialog(
            title = stringResource(R.string.login),
            message = stringResource(
                when (loginFormState.userRole) {
                    UserRole.USER_STUDENT -> R.string.login_help_student_message
                    else -> R.string.login_help_examiner_message
                }
            ),
            onDismissRequest = {
                loginViewModel.onEvent(LoginFormEvent.LoginHelpVisible(false))
            },
            onlyPositiveButton = true,
            onPositiveClick = {
                loginViewModel.onEvent(LoginFormEvent.LoginHelpVisible(false))
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
        WelcomeLayout()

        formComponent()

        Spacer(modifier = Modifier.height(MaterialTheme.space.large))
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
            WelcomeLayout()
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))

            formComponent()

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
        }
    }
}