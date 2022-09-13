package com.sweak.diplomaexam.presentation.questions_draw

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.common.UserRole
import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.components.Header
import com.sweak.diplomaexam.presentation.components.HeaderDisplayMode
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.questions_draw.components.DrawnQuestionsList
import com.sweak.diplomaexam.presentation.questions_draw.components.DrawnQuestionsOperations
import com.sweak.diplomaexam.presentation.questions_draw.components.QuestionsDrawPrompt
import com.sweak.diplomaexam.presentation.ui.theme.space
import com.sweak.diplomaexam.presentation.ui.util.WindowInfo
import com.sweak.diplomaexam.presentation.ui.util.rememberWindowInfo

@ExperimentalAnimationApi
@Composable
fun QuestionsDrawScreen(
    questionsDrawViewModel: QuestionsDrawViewModel = hiltViewModel(),
    navController: NavController
) {
    val context: Context = LocalContext.current

    LaunchedEffect(key1 = context) {
        questionsDrawViewModel.questionsConfirmedEvents.collect { event ->
            when (event) {
                is QuestionsDrawViewModel.QuestionsConfirmedEvent.Success -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val questionsDrawState = questionsDrawViewModel.state
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactQuestionsDrawScreen(
            currentUser = questionsDrawState.currentUser,
            otherUser = questionsDrawState.otherUser,
            questions = questionsDrawState.questions,
            isLoadingResponse = questionsDrawState.isLoadingResponse,
            hasStudentRequestedRedraw = questionsDrawState.hasStudentRequestedRedraw,
            waitingForDecisionFrom = questionsDrawState.waitingForDecisionFrom,
            onDrawQuestionsClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.DrawQuestions)
            },
            onAcceptDrawnQuestions = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.AcceptQuestions)
            },
            onAllowQuestionsRedraw = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.AllowRedraw)
            }
        )
    } else {
        MediumOrExpandedQuestionsDrawScreen(
            currentUser = questionsDrawState.currentUser,
            otherUser = questionsDrawState.otherUser,
            questions = questionsDrawState.questions,
            isLoadingResponse = questionsDrawState.isLoadingResponse,
            hasStudentRequestedRedraw = questionsDrawState.hasStudentRequestedRedraw,
            waitingForDecisionFrom = questionsDrawState.waitingForDecisionFrom,
            onDrawQuestionsClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.DrawQuestions)
            },
            onAcceptDrawnQuestions = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.AcceptQuestions)
            },
            onAllowQuestionsRedraw = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.AllowRedraw)
            }
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactQuestionsDrawScreen(
    currentUser: User?,
    otherUser: User?,
    questions: List<ExamQuestion>?,
    isLoadingResponse: Boolean,
    hasStudentRequestedRedraw: Boolean,
    waitingForDecisionFrom: UserRole,
    onDrawQuestionsClick: () -> Unit,
    onAcceptDrawnQuestions: () -> Unit,
    onAllowQuestionsRedraw: () -> Unit
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
            modifier = Modifier.fillMaxSize()
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
                                    areQuestionsInDrawingProcess = isLoadingResponse,
                                    modifier = Modifier.padding(
                                        start = MaterialTheme.space.large,
                                        end = MaterialTheme.space.large,
                                        bottom = MaterialTheme.space.large,
                                        top = MaterialTheme.space.medium
                                    )
                                )
                            }
                        } else {
                            Column {
                                DrawnQuestionsList(
                                    questions = questions ?: emptyList(),
                                    modifier = Modifier
                                        .padding(
                                            start = MaterialTheme.space.large,
                                            end = MaterialTheme.space.large,
                                            bottom = MaterialTheme.space.large,
                                            top = MaterialTheme.space.medium
                                        )
                                        .weight(1f)
                                )

                                DrawnQuestionsOperations(
                                    userRole = currentUser?.role ?: UserRole.USER_STUDENT,
                                    isLoadingResponse = isLoadingResponse,
                                    hasStudentRequestedRedraw = hasStudentRequestedRedraw,
                                    waitingForDecisionFrom = waitingForDecisionFrom,
                                    onAcceptQuestions = onAcceptDrawnQuestions,
                                    onRedrawQuestions = onDrawQuestionsClick,
                                    onAllowQuestionsRedraw = onAllowQuestionsRedraw,
                                    onDisallowQuestionsRedraw = onAcceptDrawnQuestions,
                                    modifier = Modifier
                                        .padding(
                                            start = MaterialTheme.space.large,
                                            end = MaterialTheme.space.large,
                                            bottom = MaterialTheme.space.large
                                        )
                                )
                            }
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
    questions: List<ExamQuestion>?,
    isLoadingResponse: Boolean,
    hasStudentRequestedRedraw: Boolean,
    waitingForDecisionFrom: UserRole,
    onDrawQuestionsClick: () -> Unit,
    onAcceptDrawnQuestions: () -> Unit,
    onAllowQuestionsRedraw: () -> Unit
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
                                    areQuestionsInDrawingProcess = isLoadingResponse,
                                    modifier = Modifier.padding(
                                        start = MaterialTheme.space.extraLarge * 2,
                                        end = MaterialTheme.space.extraLarge * 2,
                                        bottom = MaterialTheme.space.medium,
                                        top = MaterialTheme.space.small
                                    )
                                )
                            }
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                DrawnQuestionsList(
                                    questions = questions ?: emptyList(),
                                    modifier = Modifier
                                        .padding(
                                            start = MaterialTheme.space.large,
                                            end = MaterialTheme.space.large,
                                            bottom = MaterialTheme.space.medium,
                                            top = MaterialTheme.space.small
                                        )
                                        .weight(1f)
                                )

                                DrawnQuestionsOperations(
                                    userRole = currentUser?.role ?: UserRole.USER_STUDENT,
                                    isLoadingResponse = isLoadingResponse,
                                    hasStudentRequestedRedraw = hasStudentRequestedRedraw,
                                    waitingForDecisionFrom = waitingForDecisionFrom,
                                    onAcceptQuestions = onAcceptDrawnQuestions,
                                    onRedrawQuestions = onDrawQuestionsClick,
                                    onAllowQuestionsRedraw = onAllowQuestionsRedraw,
                                    onDisallowQuestionsRedraw = onAcceptDrawnQuestions,
                                    modifier = Modifier
                                        .padding(
                                            start = MaterialTheme.space.large,
                                            end = MaterialTheme.space.large,
                                            bottom = MaterialTheme.space.medium,
                                            top = MaterialTheme.space.small
                                        )
                                        .weight(1f)
                                )
                            }
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
