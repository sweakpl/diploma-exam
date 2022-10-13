package com.sweak.diplomaexam.presentation.lobby

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
import com.sweak.diplomaexam.presentation.common.WindowInfo
import com.sweak.diplomaexam.presentation.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.components.WelcomeLayout
import com.sweak.diplomaexam.presentation.lobby.components.LoggedInAsLayout
import com.sweak.diplomaexam.presentation.lobby.components.WaitingForParticipantLayout
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun LobbyScreen(
    lobbyViewModel: LobbyViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        lobbyViewModel.sessionStartEvents.collect { event ->
            when (event) {
                is LobbyViewModel.SessionStartEvent.Success -> {
                    navController.navigate(Screen.QuestionsDrawScreen.route) {
                        popUpTo(Screen.LobbyScreen.route) {
                            inclusive = true
                        }
                    }
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
            startExamSession = { lobbyViewModel.onEvent(LobbyScreenEvent.StartExam) }
        )
    } else {
        MediumOrExpandedLobbyScreen(
            user = lobbyScreenState.user,
            hasOtherUserJoined = lobbyScreenState.hasOtherUserJoinedTheLobby,
            isSessionInStartingProcess = lobbyScreenState.isSessionInStartingProcess,
            startExamSession = { lobbyViewModel.onEvent(LobbyScreenEvent.StartExam) }
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactLobbyScreen(
    user: User?,
    hasOtherUserJoined: Boolean,
    isSessionInStartingProcess: Boolean,
    startExamSession: () -> Unit
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

            WaitingForParticipantLayout(
                userRole = user?.role,
                hasOtherUserJoined = hasOtherUserJoined,
                isSessionInStartingProcess = isSessionInStartingProcess,
                startExamSession = startExamSession,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.space.medium,
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large,
                        bottom = MaterialTheme.space.large
                    )
            )

            LoggedInAsLayout(
                userEmail = user?.email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large,
                        bottom = MaterialTheme.space.large,
                    )
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedLobbyScreen(
    user: User?,
    hasOtherUserJoined: Boolean,
    isSessionInStartingProcess: Boolean,
    startExamSession: () -> Unit
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

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            WaitingForParticipantLayout(
                userRole = user?.role,
                hasOtherUserJoined = hasOtherUserJoined,
                isSessionInStartingProcess = isSessionInStartingProcess,
                startExamSession = startExamSession,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.space.medium,
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large,
                        bottom = MaterialTheme.space.large
                    )
            )

            LoggedInAsLayout(
                userEmail = user?.email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large,
                        bottom = MaterialTheme.space.medium,
                    )
            )
        }
    }
}