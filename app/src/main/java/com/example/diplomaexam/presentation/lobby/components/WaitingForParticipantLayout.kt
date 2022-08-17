package com.example.diplomaexam.presentation.lobby.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.diplomaexam.R
import com.example.diplomaexam.presentation.ui.theme.space

@Composable
fun WaitingForParticipantLayout(participant: Participant) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = MaterialTheme.space.large,
                start = MaterialTheme.space.large,
                end = MaterialTheme.space.large
            )
    ) {
        Text(
            text = stringResource(
                when (participant) {
                    Participant.STUDENT -> R.string.waiting_for_student
                    Participant.EXAMINER -> R.string.waiting_for_examiner
                }
            ),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                top = MaterialTheme.space.medium,
                bottom = MaterialTheme.space.large,
                start = MaterialTheme.space.large,
                end = MaterialTheme.space.large
            )
        )

        CircularProgressIndicator(
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(96.dp)
        )
    }
}

enum class Participant {
    STUDENT, EXAMINER
}