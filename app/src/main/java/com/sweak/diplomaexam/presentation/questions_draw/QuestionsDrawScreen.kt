package com.sweak.diplomaexam.presentation.questions_draw

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
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.common.UserRole
import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.components.Header
import com.sweak.diplomaexam.presentation.components.HeaderDisplayMode
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.questions_draw.components.DrawnQuestionsColumn
import com.sweak.diplomaexam.presentation.questions_draw.components.QuestionsDrawPrompt
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.ui.util.WindowInfo
import com.sweak.diplomaexam.presentation.ui.util.rememberWindowInfo

@ExperimentalAnimationApi
@Composable
fun QuestionsDrawScreen(
    questionsDrawViewModel: QuestionsDrawViewModel = hiltViewModel()
) {
    val questionsDrawState = questionsDrawViewModel.state
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactQuestionsDrawScreen(
            currentUser = questionsDrawState.currentUser,
            otherUser = questionsDrawState.otherUser,
            onDrawQuestionsClick = { questionsDrawViewModel.drawNewQuestions() },
            areQuestionsInDrawingProcess = questionsDrawState.areQuestionsInDrawingProcess,
            questions = questionsDrawState.questions
        )
    } else {
        MediumOrExpandedQuestionsDrawScreen(
            currentUser = questionsDrawState.currentUser,
            otherUser = questionsDrawState.otherUser,
            onDrawQuestionsClick = { questionsDrawViewModel.drawNewQuestions() },
            areQuestionsInDrawingProcess = questionsDrawState.areQuestionsInDrawingProcess,
            questions = questionsDrawState.questions
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactQuestionsDrawScreen(
    currentUser: User?,
    otherUser: User?,
    onDrawQuestionsClick: () -> Unit,
    areQuestionsInDrawingProcess: Boolean,
    questions: List<ExamQuestion>?
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
        ) {
            AnimatedContent(targetState = currentUser != null) { targetState ->
                if (targetState) {
                    AnimatedContent(targetState = questions == null) { state ->
                        if (state) {
                            if (currentUser?.role == UserRole.USER_EXAMINER) {
                                LoadingLayout(
                                    text = stringResource(R.string.student_is_drawing),
                                    modifier = Modifier.padding(
                                        start = MaterialTheme.space.large,
                                        end = MaterialTheme.space.large,
                                        bottom = MaterialTheme.space.large,
                                        top = MaterialTheme.space.medium,
                                    )
                                )
                            } else if (currentUser?.role == UserRole.USER_STUDENT) {
                                QuestionsDrawPrompt(
                                    onDrawClick = onDrawQuestionsClick,
                                    areQuestionsInDrawingProcess = areQuestionsInDrawingProcess,
                                    modifier = Modifier.padding(
                                        start = MaterialTheme.space.large,
                                        end = MaterialTheme.space.large,
                                        bottom = MaterialTheme.space.large,
                                        top = MaterialTheme.space.medium,
                                    )
                                )
                            }
                        } else {
                            DrawnQuestionsColumn(
                                questions = questions ?: emptyList(),
                                modifier = Modifier.padding(
                                    start = MaterialTheme.space.large,
                                    end = MaterialTheme.space.large,
                                    bottom = MaterialTheme.space.large,
                                    top = MaterialTheme.space.medium,
                                )
                            )
                        }
                    }
                } else {
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
    }
}

@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedQuestionsDrawScreen(
    currentUser: User?,
    otherUser: User?,
    onDrawQuestionsClick: () -> Unit,
    areQuestionsInDrawingProcess: Boolean,
    questions: List<ExamQuestion>?
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

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            AnimatedContent(targetState = currentUser != null) { targetState ->
                if (targetState) {
                    AnimatedContent(targetState = questions == null) { state ->
                        if (state) {
                            if (currentUser?.role == UserRole.USER_EXAMINER) {
                                LoadingLayout(
                                    text = stringResource(R.string.student_is_drawing),
                                    modifier = Modifier.padding(
                                        start = MaterialTheme.space.large,
                                        end = MaterialTheme.space.large,
                                        bottom = MaterialTheme.space.medium,
                                        top = MaterialTheme.space.small
                                    )
                                )
                            } else if (currentUser?.role == UserRole.USER_STUDENT) {
                                QuestionsDrawPrompt(
                                    onDrawClick = onDrawQuestionsClick,
                                    areQuestionsInDrawingProcess = areQuestionsInDrawingProcess,
                                    modifier = Modifier.padding(
                                        start = MaterialTheme.space.extraLarge * 2,
                                        end = MaterialTheme.space.extraLarge * 2,
                                        bottom = MaterialTheme.space.medium,
                                        top = MaterialTheme.space.small
                                    )
                                )
                            }
                        } else {
                            DrawnQuestionsColumn(
                                questions = questions ?: emptyList(),
                                modifier = Modifier.padding(
                                    start = MaterialTheme.space.large,
                                    end = MaterialTheme.space.large,
                                    bottom = MaterialTheme.space.medium,
                                    top = MaterialTheme.space.small
                                )
                            )
                        }
                    }
                } else {
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
    }
}
