package com.sweak.diplomaexam.presentation.screens.lobby

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.presentation.Screen
import com.sweak.diplomaexam.presentation.screens.common.UiText
import com.sweak.diplomaexam.presentation.screens.common.WindowInfo
import com.sweak.diplomaexam.presentation.screens.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.screens.common.components.ErrorLayout
import com.sweak.diplomaexam.presentation.screens.common.components.WelcomeLayout
import com.sweak.diplomaexam.presentation.screens.lobby.components.LoggedInAsLayout
import com.sweak.diplomaexam.presentation.screens.lobby.components.WaitingForParticipantLayout
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun LobbyScreen(
    lobbyViewModel: LobbyViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        lobbyViewModel.sessionStartEvents.collect {
            navController.navigate(Screen.QuestionsDrawScreen.route) {
                popUpTo(Screen.LobbyScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    val lobbyScreenState = lobbyViewModel.state
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactLobbyScreen(
            user = lobbyScreenState.user,
            hasOtherUserJoined = lobbyScreenState.hasOtherUserJoinedTheLobby,
            isSessionInStartingProcess = lobbyScreenState.isSessionInStartingProcess,
            errorMessage = lobbyScreenState.errorMessage,
            startExamSession = { lobbyViewModel.onEvent(LobbyScreenEvent.StartExam) },
            onRetryClick = { lobbyViewModel.onEvent(LobbyScreenEvent.RetryAfterError) }
        )
    } else {
        MediumOrExpandedLobbyScreen(
            user = lobbyScreenState.user,
            hasOtherUserJoined = lobbyScreenState.hasOtherUserJoinedTheLobby,
            isSessionInStartingProcess = lobbyScreenState.isSessionInStartingProcess,
            errorMessage = lobbyScreenState.errorMessage,
            startExamSession = { lobbyViewModel.onEvent(LobbyScreenEvent.StartExam) },
            onRetryClick = { lobbyViewModel.onEvent(LobbyScreenEvent.RetryAfterError) }
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactLobbyScreen(
    user: User?,
    hasOtherUserJoined: Boolean,
    isSessionInStartingProcess: Boolean,
    errorMessage: UiText?,
    startExamSession: () -> Unit,
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
            modifier = Modifier
                .fillMaxHeight()
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

            AnimatedContent(
                targetState = errorMessage == null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.space.medium,
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large,
                        bottom = MaterialTheme.space.large
                    )
            ) { targetState ->
                Column(verticalArrangement = Arrangement.Top) {
                    if (targetState) {
                        WaitingForParticipantLayout(
                            userRole = user?.role,
                            hasOtherUserJoined = hasOtherUserJoined,
                            isSessionInStartingProcess = isSessionInStartingProcess,
                            startExamSession = startExamSession,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = MaterialTheme.space.large)
                        )

                        LoggedInAsLayout(
                            userEmail = user?.email,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        ErrorLayout(
                            onRetryClick = onRetryClick,
                            text = errorMessage?.asString()
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedLobbyScreen(
    user: User?,
    hasOtherUserJoined: Boolean,
    isSessionInStartingProcess: Boolean,
    errorMessage: UiText?,
    startExamSession: () -> Unit,
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
            targetState = errorMessage == null,
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    top = MaterialTheme.space.medium,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large,
                    bottom = MaterialTheme.space.medium
                )
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) { targetState ->
            Column(verticalArrangement = Arrangement.Center) {
                if (targetState) {
                    WaitingForParticipantLayout(
                        userRole = user?.role,
                        hasOtherUserJoined = hasOtherUserJoined,
                        isSessionInStartingProcess = isSessionInStartingProcess,
                        startExamSession = startExamSession,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.space.large)
                    )

                    LoggedInAsLayout(
                        userEmail = user?.email,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    ErrorLayout(
                        onRetryClick = onRetryClick,
                        text = errorMessage?.asString()
                    )
                }
            }
        }
    }
}