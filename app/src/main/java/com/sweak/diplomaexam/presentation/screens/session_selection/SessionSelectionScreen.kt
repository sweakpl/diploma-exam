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
import com.sweak.diplomaexam.presentation.screens.common.WindowInfo
import com.sweak.diplomaexam.presentation.screens.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.screens.components.Dialog
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
            onSessionSelected = {
                sessionSelectionViewModel.onEvent(
                    SessionSelectionScreenEvent.SelectAvailableSession(it)
                )
            }
        )
    } else {
        MediumOrExpandedSessionSelectionScreen(
            availableSessions = sessionSelectionScreenState.availableSessions,
            isLoadingResponse = sessionSelectionScreenState.isLoadingResponse,
            onSessionSelected = {
                sessionSelectionViewModel.onEvent(
                    SessionSelectionScreenEvent.SelectAvailableSession(it)
                )
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

    if (sessionSelectionScreenState.loadingErrorDialogVisible) {
        Dialog(
            title = stringResource(R.string.error_occurred_general),
            message = stringResource(R.string.error_occurred_general_description),
            onDismissRequest = {
                sessionSelectionViewModel.onEvent(SessionSelectionScreenEvent.RetryAfterError)
            },
            onlyPositiveButton = true,
            onPositiveClick = {
                sessionSelectionViewModel.onEvent(SessionSelectionScreenEvent.RetryAfterError)
            },
            positiveButtonText = stringResource(R.string.retry),
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactSessionSelectionScreen(
    availableSessions: List<AvailableSession>?,
    isLoadingResponse: Boolean,
    onSessionSelected: (AvailableSession) -> Unit
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
                targetState = isLoadingResponse || availableSessions == null,
                modifier = Modifier.padding(all = MaterialTheme.space.large)
            ) { state ->
                if (state) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LoadingLayout()
                    }
                } else {
                    SessionSelectionPanel(
                        availableSessions = availableSessions ?: emptyList(),
                        onSessionSelected = onSessionSelected
                    )
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
    onSessionSelected: (AvailableSession) -> Unit
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
            targetState = isLoadingResponse || availableSessions == null,
            modifier = Modifier
                .padding(all = MaterialTheme.space.large)
                .weight(1f)
        ) { state ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                if (state) {
                    LoadingLayout()
                } else {
                    SessionSelectionPanel(
                        availableSessions = availableSessions ?: emptyList(),
                        onSessionSelected = onSessionSelected
                    )
                }
            }
        }
    }
}
