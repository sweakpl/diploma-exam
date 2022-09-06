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
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.components.WelcomeLayout
import com.sweak.diplomaexam.presentation.lobby.components.LoggedInAsLayout
import com.sweak.diplomaexam.presentation.lobby.components.WaitingForParticipantLayout
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.ui.util.WindowInfo
import com.sweak.diplomaexam.presentation.ui.util.rememberWindowInfo

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
                    navController.popBackStack()
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
            startExamSession = { lobbyViewModel.startSession() }
        )
    } else {
        MediumOrExpandedLobbyScreen(
            user = lobbyScreenState.user,
            hasOtherUserJoined = lobbyScreenState.hasOtherUserJoinedTheLobby,
            startExamSession = { lobbyViewModel.startSession() }
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactLobbyScreen(
    user: User?,
    hasOtherUserJoined: Boolean,
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
            WelcomeLayout()

            WaitingForParticipantLayout(
                userRole = user?.role,
                hasOtherUserJoined = hasOtherUserJoined,
                startExamSession = startExamSession
            )

            LoggedInAsLayout(userEmail = user?.email)

            Spacer(modifier = Modifier.height(MaterialTheme.space.large))
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedLobbyScreen(
    user: User?,
    hasOtherUserJoined: Boolean,
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
            WelcomeLayout()
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))

            WaitingForParticipantLayout(
                userRole = user?.role,
                hasOtherUserJoined = hasOtherUserJoined,
                startExamSession = startExamSession
            )

            LoggedInAsLayout(userEmail = user?.email)

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
        }
    }
}