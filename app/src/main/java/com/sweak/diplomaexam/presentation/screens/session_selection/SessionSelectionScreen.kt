package com.sweak.diplomaexam.presentation.screens.session_selection

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.presentation.Screen
import com.sweak.diplomaexam.presentation.screens.common.UiText
import com.sweak.diplomaexam.presentation.screens.common.WindowInfo
import com.sweak.diplomaexam.presentation.screens.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.screens.components.Dialog
import com.sweak.diplomaexam.presentation.screens.components.ErrorLayout
import com.sweak.diplomaexam.presentation.screens.components.LoadingLayout
import com.sweak.diplomaexam.presentation.screens.components.WelcomeLayout
import com.sweak.diplomaexam.presentation.screens.session_selection.components.SessionSelectionPanel
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun SessionSelectionScreen(
    sessionSelectionViewModel: SessionSelectionViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        sessionSelectionViewModel.sessionConfirmedEvents.collect { event ->
            when (event) {
                is SessionSelectionViewModel.SessionConfirmedEvent.Success -> {
                    navController.navigate(Screen.LobbyScreen.route) {
                        popUpTo(Screen.SessionSelectionScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    val sessionSelectionScreenState = sessionSelectionViewModel.state
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactSessionSelectionScreen(
            availableSessions = sessionSelectionScreenState.availableSessions,
            isLoadingResponse = sessionSelectionScreenState.isLoadingResponse,
            errorMessage = sessionSelectionScreenState.errorMessage,
            onSessionSelected = {
                sessionSelectionViewModel.onEvent(
                    SessionSelectionScreenEvent.SelectAvailableSession(it)
                )
            },
            onRetryClick = {
                sessionSelectionViewModel.onEvent(SessionSelectionScreenEvent.RetryAfterError)
            }
        )
    } else {
        MediumOrExpandedSessionSelectionScreen(
            availableSessions = sessionSelectionScreenState.availableSessions,
            isLoadingResponse = sessionSelectionScreenState.isLoadingResponse,
            errorMessage = sessionSelectionScreenState.errorMessage,
            onSessionSelected = {
                sessionSelectionViewModel.onEvent(
                    SessionSelectionScreenEvent.SelectAvailableSession(it)
                )
            },
            onRetryClick = {
                sessionSelectionViewModel.onEvent(SessionSelectionScreenEvent.RetryAfterError)
            }
        )
    }

    if (sessionSelectionScreenState.sessionSelectionConfirmationDialogVisible &&
            sessionSelectionScreenState.selectedSession != null
    ) {
        Dialog(
            title = stringResource(R.string.confirm_selection),
            message = stringResource(
                R.string.do_you_want_to_start_session_with_student,
                sessionSelectionScreenState.selectedSession.studentEmail
            ),
            onDismissRequest = {
                sessionSelectionViewModel.onEvent(
                    SessionSelectionScreenEvent.HideSessionSelectionConfirmationDialog(
                        isConfirmingAfterHiding = false
                    )
                )
            },
            onPositiveClick = {
                sessionSelectionViewModel.onEvent(
                    SessionSelectionScreenEvent.HideSessionSelectionConfirmationDialog(
                        isConfirmingAfterHiding = true
                    )
                )
                sessionSelectionViewModel.onEvent(
                    SessionSelectionScreenEvent.ConfirmSessionSelection
                )
            },
            positiveButtonText = stringResource(R.string.yes),
            onNegativeClick = {
                sessionSelectionViewModel.onEvent(
                    SessionSelectionScreenEvent.HideSessionSelectionConfirmationDialog(
                        isConfirmingAfterHiding = false
                    )
                )
            },
            negativeButtonText = stringResource(R.string.no)
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactSessionSelectionScreen(
    availableSessions: List<AvailableSession>?,
    isLoadingResponse: Boolean,
    errorMessage: UiText?,
    onSessionSelected: (AvailableSession) -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
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
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            WelcomeLayout(
                text = stringResource(R.string.select_session_student),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.space.large)
            )

            AnimatedContent(
                targetState =
                if (errorMessage != null) AnimatedComponentTargetState.ERROR
                else if (isLoadingResponse || availableSessions == null)
                    AnimatedComponentTargetState.LOADING
                else AnimatedComponentTargetState.SUCCESS,
                modifier = Modifier.padding(all = MaterialTheme.space.large)
            ) { targetState ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when (targetState) {
                        AnimatedComponentTargetState.SUCCESS ->
                            SessionSelectionPanel(
                                availableSessions = availableSessions ?: emptyList(),
                                onSessionSelected = onSessionSelected
                            )
                        AnimatedComponentTargetState.LOADING -> LoadingLayout()
                        AnimatedComponentTargetState.ERROR ->
                            ErrorLayout(
                                onRetryClick = onRetryClick,
                                text = errorMessage?.asString(),
                                modifier = Modifier.verticalScroll(state = rememberScrollState())
                            )
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedSessionSelectionScreen(
    availableSessions: List<AvailableSession>?,
    isLoadingResponse: Boolean,
    errorMessage: UiText?,
    onSessionSelected: (AvailableSession) -> Unit,
    onRetryClick: () -> Unit
) {
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
                text = stringResource(R.string.select_session_student),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large,
                        bottom = MaterialTheme.space.medium
                    )
            )
        }

        AnimatedContent(
            targetState =
            if (errorMessage != null) AnimatedComponentTargetState.ERROR
            else if (isLoadingResponse || availableSessions == null)
                AnimatedComponentTargetState.LOADING
            else AnimatedComponentTargetState.SUCCESS,
            modifier = Modifier
                .padding(all = MaterialTheme.space.large)
                .weight(1f)
        ) { targetState ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                when (targetState) {
                    AnimatedComponentTargetState.SUCCESS ->
                        SessionSelectionPanel(
                            availableSessions = availableSessions ?: emptyList(),
                            onSessionSelected = onSessionSelected
                        )
                    AnimatedComponentTargetState.LOADING -> LoadingLayout()
                    AnimatedComponentTargetState.ERROR ->
                        ErrorLayout(
                            onRetryClick = onRetryClick,
                            text = errorMessage?.asString(),
                            modifier = Modifier.verticalScroll(state = rememberScrollState())
                        )
                }
            }
        }
    }
}

private enum class AnimatedComponentTargetState { ERROR, LOADING, SUCCESS }