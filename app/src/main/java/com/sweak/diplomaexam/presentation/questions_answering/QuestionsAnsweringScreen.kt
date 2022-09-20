package com.sweak.diplomaexam.presentation.questions_answering

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.components.Header
import com.sweak.diplomaexam.presentation.components.HeaderDisplayMode
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.ui.util.WindowInfo
import com.sweak.diplomaexam.presentation.ui.util.rememberWindowInfo

@Composable
fun QuestionsAnsweringScreen() {
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactQuestionsAnsweringScreen()
    } else {
        MediumOrExpandedQuestionsAnsweringScreen()
    }
}
@Composable
fun CompactQuestionsAnsweringScreen() {
    val usersInSession = mutableListOf<User>()

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
        Header(
            titleText = stringResource(R.string.answering_questions),
            displayMode = HeaderDisplayMode.COMPACT,
            usersInSession = usersInSession,
            onProceedClickListener = { /* TODO: Handle proceed */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large,
                    bottom = MaterialTheme.space.medium,
                    top = MaterialTheme.space.large,
                )
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingLayout(
                text = stringResource(R.string.loading),
                modifier = Modifier.padding(
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large,
                    bottom = MaterialTheme.space.large,
                    top = MaterialTheme.space.medium,
                )
            )
        }
    }
}

@Composable
fun MediumOrExpandedQuestionsAnsweringScreen() {
    val usersInSession = mutableListOf<User>()

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
        Header(
            titleText = stringResource(R.string.answering_questions),
            displayMode = HeaderDisplayMode.MEDIUM_OR_EXPANDED,
            usersInSession = usersInSession,
            onProceedClickListener = { /* TODO: Handle proceed */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large,
                    bottom = MaterialTheme.space.small,
                    top = MaterialTheme.space.large,
                )
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingLayout(
                text = stringResource(R.string.loading),
                modifier = Modifier.padding(
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large,
                    bottom = MaterialTheme.space.medium,
                    top = MaterialTheme.space.small
                )
            )
        }
    }
}
