package com.sweak.diplomaexam.presentation.screens.login.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.screens.components.ThickWhiteButton
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.screens.common.UiText

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun LoginForm(
    emailAddress: String,
    onEmailAddressChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleClick: () -> Unit,
    errorMessage: UiText?,
    onLoginClick: () -> Unit,
    isAuthorizing: Boolean,
    onLoginHelpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = MaterialTheme.space.large)
        ) {
            IconButton(
                onClick = onLoginHelpClick,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = MaterialTheme.space.small)
            ) {
                Icon(
                    imageVector = Icons.Default.Help,
                    contentDescription = "Login help icon",
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(size = 24.dp)
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
            enabled = !isAuthorizing,
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
                IconButton(
                    onClick = { onEmailAddressChange("") },
                    enabled = !isAuthorizing
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear text"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.space.medium)
        )

        OutlinedTextField(
            isError = errorMessage != null,
            value = password,
            onValueChange = { onPasswordChange(it) },
            enabled = !isAuthorizing,
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

                IconButton(
                    onClick = onPasswordVisibleClick,
                    enabled = !isAuthorizing
                ) {
                    Icon(imageVector = image, description)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.space.large)
        )

        AnimatedVisibility(visible = errorMessage != null) {
            Text(
                text = errorMessage?.asString() ?: "",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(bottom = MaterialTheme.space.large)
            )
        }

        AnimatedContent(targetState = isAuthorizing) { targetState ->
            if (targetState) {
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
        }
    }
}