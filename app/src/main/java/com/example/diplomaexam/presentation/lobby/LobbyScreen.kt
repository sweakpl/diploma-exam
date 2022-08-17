package com.example.diplomaexam.presentation.lobby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.diplomaexam.presentation.components.WelcomeLayout
import com.example.diplomaexam.presentation.lobby.components.LoggedInAsLayout
import com.example.diplomaexam.presentation.lobby.components.Participant
import com.example.diplomaexam.presentation.lobby.components.WaitingForParticipantLayout
import com.example.diplomaexam.presentation.ui.theme.space
import com.example.diplomaexam.presentation.ui.util.WindowInfo
import com.example.diplomaexam.presentation.ui.util.rememberWindowInfo

@Composable
fun LobbyScreen() {
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactLobbyScreen()
    } else {
        MediumOrExpandedLobbyScreen()
    }
}

@Composable
fun CompactLobbyScreen() {
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

            WaitingForParticipantLayout(participant = Participant.STUDENT)

            LoggedInAsLayout(userEmail = "adam.nowak@pk.edu.pl")

            Spacer(modifier = Modifier.height(MaterialTheme.space.large))
        }
    }
}

@Composable
fun MediumOrExpandedLobbyScreen() {
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

            WaitingForParticipantLayout(participant = Participant.EXAMINER)

            LoggedInAsLayout(userEmail = "adam.nowak@pk.edu.pl")

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
        }
    }
}