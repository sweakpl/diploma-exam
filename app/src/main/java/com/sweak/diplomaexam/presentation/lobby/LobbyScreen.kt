package com.sweak.diplomaexam.presentation.lobby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.components.WelcomeLayout
import com.sweak.diplomaexam.presentation.lobby.components.LoggedInAsLayout
import com.sweak.diplomaexam.presentation.lobby.components.WaitingForParticipantLayout
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.ui.util.WindowInfo
import com.sweak.diplomaexam.presentation.ui.util.rememberWindowInfo

@Composable
fun LobbyScreen(
    lobbyViewModel: LobbyViewModel = hiltViewModel()
) {
    val lobbyScreenState = lobbyViewModel.state
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactLobbyScreen(
            user = lobbyScreenState.user
        )
    } else {
        MediumOrExpandedLobbyScreen(
            user = lobbyScreenState.user
        )
    }
}

@Composable
fun CompactLobbyScreen(
    user: User?
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

            WaitingForParticipantLayout(userRole = user?.role)

            LoggedInAsLayout(userEmail = user?.email)

            Spacer(modifier = Modifier.height(MaterialTheme.space.large))
        }
    }
}

@Composable
fun MediumOrExpandedLobbyScreen(
    user: User?
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

            WaitingForParticipantLayout(userRole = user?.role)

            LoggedInAsLayout(userEmail = user?.email)

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
        }
    }
}