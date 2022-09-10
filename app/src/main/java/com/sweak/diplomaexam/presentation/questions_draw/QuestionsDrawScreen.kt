package com.sweak.diplomaexam.presentation.questions_draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.common.UserRole
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.components.Header
import com.sweak.diplomaexam.presentation.components.HeaderDisplayMode
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.questions_draw.components.QuestionsDrawPrompt
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.ui.util.WindowInfo
import com.sweak.diplomaexam.presentation.ui.util.rememberWindowInfo

@Composable
fun QuestionsDrawScreen(
    questionsDrawViewModel: QuestionsDrawViewModel = hiltViewModel()
) {
    val questionsDrawState = questionsDrawViewModel.state
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactQuestionsDrawScreen(
            currentUser = questionsDrawState.currentUser,
            otherUser = questionsDrawState.otherUser
        )
    } else {
        MediumOrExpandedQuestionsDrawScreen(
            currentUser = questionsDrawState.currentUser,
            otherUser = questionsDrawState.otherUser
        )
    }
}

@Composable
fun CompactQuestionsDrawScreen(
    currentUser: User?,
    otherUser: User?
) {
    val usersInSession = mutableListOf<User>()
    currentUser?.let { usersInSession.add(it) }
    otherUser?.let { usersInSession.add(it) }

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
            displayMode = HeaderDisplayMode.COMPACT,
            usersInSession = usersInSession,
            proceedButtonEnabled = false,
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (currentUser == null || currentUser.role == UserRole.USER_EXAMINER) {
                LoadingLayout(
                    text = stringResource(
                        if (currentUser != null) R.string.student_is_drawing else R.string.loading
                    ),
                    modifier = Modifier.padding(
                        start = MaterialTheme.space.large,
                        end = MaterialTheme.space.large,
                        bottom = MaterialTheme.space.large,
                        top = MaterialTheme.space.medium,
                    )
                )
            } else {
                QuestionsDrawPrompt(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.space.large,
                            end = MaterialTheme.space.large,
                            bottom = MaterialTheme.space.large,
                            top = MaterialTheme.space.medium,
                        )
                )
            }
        }
    }
}

@Composable
fun MediumOrExpandedQuestionsDrawScreen(
    currentUser: User?,
    otherUser: User?
) {
    val usersInSession = mutableListOf<User>()
    currentUser?.let { usersInSession.add(it) }
    otherUser?.let { usersInSession.add(it) }

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
            displayMode = HeaderDisplayMode.MEDIUM_OR_EXPANDED,
            usersInSession = usersInSession,
            proceedButtonEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large,
                    bottom = MaterialTheme.space.small,
                    top = MaterialTheme.space.large,
                )
        )

        if (currentUser == null ||
            currentUser.role == UserRole.USER_STUDENT ||
            currentUser.role == UserRole.USER_EXAMINER
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (currentUser == null || currentUser.role == UserRole.USER_EXAMINER) {
                    LoadingLayout(
                        text = stringResource(
                            if (currentUser != null) R.string.student_is_drawing else R.string.loading
                        ),
                        modifier = Modifier.padding(
                            start = MaterialTheme.space.large,
                            end = MaterialTheme.space.large,
                            bottom = MaterialTheme.space.medium,
                            top = MaterialTheme.space.small
                        )
                    )
                } else {
                    QuestionsDrawPrompt(
                        modifier = Modifier
                            .padding(
                                start = MaterialTheme.space.extraLarge * 2,
                                end = MaterialTheme.space.extraLarge * 2,
                                bottom = MaterialTheme.space.medium,
                                top = MaterialTheme.space.small
                            )
                    )
                }
            }
        }
    }
}
