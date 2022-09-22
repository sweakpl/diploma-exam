package com.sweak.diplomaexam.presentation.questions_answering

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.common.UserRole
import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.components.Header
import com.sweak.diplomaexam.presentation.components.HeaderDisplayMode
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.questions_answering.components.ExaminerQuestionsPanel
import com.sweak.diplomaexam.presentation.questions_answering.components.ExaminerQuestionsPanelDisplayMode
import com.sweak.diplomaexam.presentation.questions_answering.components.StudentQuestionsPanel
import com.sweak.diplomaexam.presentation.questions_answering.components.StudentQuestionsPanelDisplayMode
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.ui.util.WindowInfo
import com.sweak.diplomaexam.presentation.ui.util.rememberWindowInfo

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun QuestionsAnsweringScreen(
    questionsAnsweringViewModel: QuestionsAnsweringViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()
    val questionsAnsweringState = questionsAnsweringViewModel.state

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactQuestionsAnsweringScreen(
            currentUser = questionsAnsweringState.currentUser,
            otherUser = questionsAnsweringState.otherUser,
            questions = questionsAnsweringState.questions,
            isLoadingResponse = questionsAnsweringState.isLoadingResponse,
            isWaitingForStudentReadiness = questionsAnsweringState.isWaitingForStudentReadiness
        )
    } else {
        MediumOrExpandedQuestionsAnsweringScreen(
            currentUser = questionsAnsweringState.currentUser,
            otherUser = questionsAnsweringState.otherUser,
            questions = questionsAnsweringState.questions,
            isLoadingResponse = questionsAnsweringState.isLoadingResponse,
            isWaitingForStudentReadiness = questionsAnsweringState.isWaitingForStudentReadiness
        )
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun CompactQuestionsAnsweringScreen(
    currentUser: User?,
    otherUser: User?,
    questions: List<ExamQuestion>,
    isLoadingResponse: Boolean,
    isWaitingForStudentReadiness: Boolean
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
            titleText = stringResource(R.string.answering_questions),
            displayMode = HeaderDisplayMode.COMPACT,
            usersInSession = usersInSession,
            proceedButtonEnabled = currentUser != null && currentUser.role == UserRole.USER_EXAMINER,
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

        AnimatedContent(
            targetState = currentUser != null,
            modifier = Modifier.padding(all = MaterialTheme.space.large)
        ) { targetState ->
            if (targetState) {
                if (currentUser!!.role == UserRole.USER_STUDENT) {
                    StudentQuestionsPanel(
                        questions = questions,
                        displayMode = StudentQuestionsPanelDisplayMode.COMPACT,
                        isLoadingResponse = isLoadingResponse,
                        isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                        onConfirmReadiness = {}
                    )
                } else {
                    ExaminerQuestionsPanel(
                        questions = questions,
                        displayMode = ExaminerQuestionsPanelDisplayMode.COMPACT,
                        isLoadingResponse = isLoadingResponse,
                        isWaitingForStudentReadiness = isWaitingForStudentReadiness
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoadingLayout(text = stringResource(R.string.loading))
                }
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedQuestionsAnsweringScreen(
    currentUser: User?,
    otherUser: User?,
    questions: List<ExamQuestion>,
    isLoadingResponse: Boolean,
    isWaitingForStudentReadiness: Boolean
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
            titleText = stringResource(R.string.answering_questions),
            displayMode = HeaderDisplayMode.MEDIUM_OR_EXPANDED,
            usersInSession = usersInSession,
            proceedButtonEnabled = currentUser != null && currentUser.role == UserRole.USER_EXAMINER,
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

        AnimatedContent(
            targetState = currentUser != null,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.space.large,
                vertical = MaterialTheme.space.medium
            )
        ) { targetState ->
            if (targetState) {
                if (currentUser!!.role == UserRole.USER_STUDENT) {
                    StudentQuestionsPanel(
                        questions = questions,
                        displayMode = StudentQuestionsPanelDisplayMode.MEDIUM_OR_EXPANDED,
                        isLoadingResponse = isLoadingResponse,
                        isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                        onConfirmReadiness = {}
                    )
                } else {
                    ExaminerQuestionsPanel(
                        questions = questions,
                        displayMode = ExaminerQuestionsPanelDisplayMode.MEDIUM_OR_EXPANDED,
                        isLoadingResponse = isLoadingResponse,
                        isWaitingForStudentReadiness = isWaitingForStudentReadiness
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoadingLayout(text = stringResource(R.string.loading))
                }
            }
        }
    }
}
