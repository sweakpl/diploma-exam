package com.example.egzamindyplomowy.presentation.introduction.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.common.LOGIN_MODE_EXAMINER
import com.example.egzamindyplomowy.common.LOGIN_MODE_STUDENT
import com.example.egzamindyplomowy.presentation.UiText
import com.example.egzamindyplomowy.presentation.WindowInfo
import com.example.egzamindyplomowy.presentation.components.Dialog
import com.example.egzamindyplomowy.presentation.components.ThickWhiteButton
import com.example.egzamindyplomowy.presentation.components.WelcomeLayout
import com.example.egzamindyplomowy.presentation.rememberWindowInfo
import com.example.egzamindyplomowy.presentation.ui.theme.space
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    loginMode: String,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        loginViewModel.authenticateEvents.collect { event ->
            when (event) {
                is LoginViewModel.AuthenticationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Login successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    val state = loginViewModel.state
    val windowInfo = rememberWindowInfo()

    var loginHelpDialogVisible by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactLoginScreen(
            emailAddress = state.email,
            onEmailAddressChange = { loginViewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
            password = state.password,
            onPasswordChange = { loginViewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
            passwordVisible = passwordVisible,
            onPasswordVisibleClick = { passwordVisible = !passwordVisible },
            errorMessage = state.errorMessage,
            onLoginClick = { loginViewModel.onEvent(LoginFormEvent.Login) },
            isAuthorizing = state.isAuthorizing,
            onLoginHelpClick = { loginHelpDialogVisible = true }
        )
    } else {
        MediumOrExpandedLoginScreen(
            emailAddress = state.email,
            onEmailAddressChange = { loginViewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
            password = state.password,
            onPasswordChange = { loginViewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
            passwordVisible = passwordVisible,
            onPasswordVisibleClick = { passwordVisible = !passwordVisible },
            errorMessage = state.errorMessage,
            onLoginClick = { loginViewModel.onEvent(LoginFormEvent.Login) },
            isAuthorizing = state.isAuthorizing,
            onLoginHelpClick = { loginHelpDialogVisible = true }
        )
    }

    if (loginHelpDialogVisible) {
        Dialog(
            onDismissRequest = { loginHelpDialogVisible = false },
            onPositiveClick = { loginHelpDialogVisible = false },
            onNegativeClick = null,
            title = stringResource(R.string.login),
            message = stringResource(
                when (loginMode) {
                    LOGIN_MODE_STUDENT -> R.string.login_help_student_message
                    else -> R.string.login_help_examiner_message
                }
            ),
            positiveButtonText = stringResource(android.R.string.ok),
            negativeButtonText = null,
            onlyPositiveButton = true
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun CompactLoginScreen(
    emailAddress: String,
    onEmailAddressChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleClick: () -> Unit,
    errorMessage: UiText?,
    onLoginClick: () -> Unit,
    isAuthorizing: Boolean,
    onLoginHelpClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                bottom = MaterialTheme.space.large,
                start = MaterialTheme.space.large,
                end = MaterialTheme.space.large
            )
        ) {
            IconButton(
                onClick = onLoginHelpClick,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = MaterialTheme.space.small)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_help),
                    contentDescription = "Login help icon",
                    tint = MaterialTheme.colors.onPrimary,
                )
            }

            Text(
                text = stringResource(R.string.please_login),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
            )
        }

        OutlinedTextField(
            isError = errorMessage != null,
            value = emailAddress,
            onValueChange = { onEmailAddressChange(it) },
            label = {
                Text(
                    text = stringResource(R.string.email),
                    style = MaterialTheme.typography.body1
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                trailingIconColor = MaterialTheme.colors.onPrimary,
                focusedBorderColor = MaterialTheme.colors.onPrimary,
                unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                backgroundColor = Color.Transparent,
                errorBorderColor = MaterialTheme.colors.error,
                errorTrailingIconColor = MaterialTheme.colors.error,
                errorLabelColor = MaterialTheme.colors.error,
                errorCursorColor = MaterialTheme.colors.error
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            trailingIcon = {
                IconButton(onClick = { onEmailAddressChange("") }) {
                    Icon(imageVector = Icons.Default.Clear, "Clear text")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = MaterialTheme.space.medium,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large
                )
        )

        OutlinedTextField(
            isError = errorMessage != null,
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = {
                Text(
                    text = stringResource(R.string.password),
                    style = MaterialTheme.typography.body1
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                trailingIconColor = MaterialTheme.colors.onPrimary,
                focusedBorderColor = MaterialTheme.colors.onPrimary,
                unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                backgroundColor = Color.Transparent,
                errorBorderColor = MaterialTheme.colors.error,
                errorTrailingIconColor = MaterialTheme.colors.error,
                errorLabelColor = MaterialTheme.colors.error,
                errorCursorColor = MaterialTheme.colors.error
            ),
            singleLine = true,
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onLoginClick()
                    softwareKeyboardController?.hide()
                }
            ),
            trailingIcon = {
                val image = if (passwordVisible) {
                    Icons.Default.Visibility
                } else {
                    Icons.Default.VisibilityOff
                }

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = onPasswordVisibleClick) {
                    Icon(imageVector = image, description)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = MaterialTheme.space.large,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large
                )
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage.asString(),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(
                    bottom = MaterialTheme.space.large,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large
                )
            )
        }

        if (isAuthorizing) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(52.dp)
            )
        } else {
            ThickWhiteButton(
                text = stringResource(R.string.login),
                onClick = onLoginClick
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.space.large))
    }
}

@ExperimentalComposeUiApi
@Composable
fun MediumOrExpandedLoginScreen(
    emailAddress: String,
    onEmailAddressChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleClick: () -> Unit,
    errorMessage: UiText?,
    onLoginClick: () -> Unit,
    isAuthorizing: Boolean,
    onLoginHelpClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    top = MaterialTheme.space.medium,
                    bottom = MaterialTheme.space.large,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large
                )
            ) {
                IconButton(
                    onClick = onLoginHelpClick,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = MaterialTheme.space.small)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_help),
                        contentDescription = "Login help icon",
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }

                Text(
                    text = stringResource(R.string.please_login),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                )
            }

            OutlinedTextField(
                isError = errorMessage != null,
                value = emailAddress,
                onValueChange = { onEmailAddressChange(it) },
                label = {
                    Text(
                        text = stringResource(R.string.email),
                        style = MaterialTheme.typography.body1
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onPrimary,
                    trailingIconColor = MaterialTheme.colors.onPrimary,
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = Color.Transparent,
                    errorBorderColor = MaterialTheme.colors.error,
                    errorTrailingIconColor = MaterialTheme.colors.error,
                    errorLabelColor = MaterialTheme.colors.error,
                    errorCursorColor = MaterialTheme.colors.error
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                trailingIcon = {
                    IconButton(onClick = { onEmailAddressChange("") }) {
                        Icon(imageVector = Icons.Default.Clear, "Clear text")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = MaterialTheme.space.medium,
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large
                    )
            )

            OutlinedTextField(
                isError = errorMessage != null,
                value = password,
                onValueChange = { onPasswordChange(it) },
                label = {
                    Text(
                        text = stringResource(R.string.password),
                        style = MaterialTheme.typography.body1
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onPrimary,
                    trailingIconColor = MaterialTheme.colors.onPrimary,
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = Color.Transparent,
                    errorBorderColor = MaterialTheme.colors.error,
                    errorTrailingIconColor = MaterialTheme.colors.error,
                    errorLabelColor = MaterialTheme.colors.error,
                    errorCursorColor = MaterialTheme.colors.error
                ),
                singleLine = true,
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onLoginClick()
                        softwareKeyboardController?.hide()
                    }
                ),
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    }

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = onPasswordVisibleClick) {
                        Icon(imageVector = image, description)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = MaterialTheme.space.large,
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large
                    )
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage.asString(),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(
                        bottom = MaterialTheme.space.large,
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large
                    )
                )
            }

            if (isAuthorizing) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .size(52.dp)
                        .padding(
                            start = MaterialTheme.space.large,
                            end = MaterialTheme.space.large
                        )
                )
            } else {
                ThickWhiteButton(
                    text = stringResource(R.string.login),
                    onClick = onLoginClick
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
        }
    }
}