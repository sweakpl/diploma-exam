package com.example.egzamindyplomowy.presentation.introduction.login

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.presentation.WindowInfo
import com.example.egzamindyplomowy.presentation.introduction.components.ThickWhiteButton
import com.example.egzamindyplomowy.presentation.introduction.components.WelcomeLayout
import com.example.egzamindyplomowy.presentation.rememberWindowInfo
import com.example.egzamindyplomowy.presentation.ui.theme.space

@Composable
fun LoginScreen(
    loginMode: String
) {
    val windowInfo = rememberWindowInfo()

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailAddress by remember { mutableStateOf("") }

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactLoginScreen(
            emailAddress = emailAddress,
            onEmailAddressChange = { emailAddress = it },
            password = password,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibleClick = { passwordVisible = !passwordVisible },
            onLoginClick = { /* TODO: login the user */ }
        )
    } else {
        MediumOrExpandedLoginScreen(
            emailAddress = emailAddress,
            onEmailAddressChange = { emailAddress = it },
            password = password,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibleClick = { passwordVisible = !passwordVisible },
            onLoginClick = { /* TODO: login the user */ }
        )
    }
}

@Composable
fun CompactLoginScreen(
    emailAddress: String,
    onEmailAddressChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

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

        Text(
            text = stringResource(R.string.please_login),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                bottom = MaterialTheme.space.large,
                start = MaterialTheme.space.large,
                end = MaterialTheme.space.large
            )
        )

        OutlinedTextField(
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
                backgroundColor = Color.Transparent
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
                backgroundColor = Color.Transparent
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
                onDone = { onLoginClick() }
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

        ThickWhiteButton(
            text = stringResource(R.string.login),
            onClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(MaterialTheme.space.large))
    }
}

@Composable
fun MediumOrExpandedLoginScreen(
    emailAddress: String,
    onEmailAddressChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

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
            Text(
                text = stringResource(R.string.please_login),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    top = MaterialTheme.space.medium,
                    bottom = MaterialTheme.space.large,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large
                )
            )

            OutlinedTextField(
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
                    backgroundColor = Color.Transparent
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
                    backgroundColor = Color.Transparent
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
                    onDone = { onLoginClick() }
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

            ThickWhiteButton(
                text = stringResource(R.string.login),
                onClick = onLoginClick
            )

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
        }
    }
}