package com.sweak.diplomaexam.presentation.screens.questions_draw

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
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.presentation.Screen
import com.sweak.diplomaexam.presentation.screens.common.UiText
import com.sweak.diplomaexam.presentation.screens.common.WindowInfo
import com.sweak.diplomaexam.presentation.screens.common.components.*
import com.sweak.diplomaexam.presentation.screens.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.screens.questions_draw.components.DrawnQuestionsList
import com.sweak.diplomaexam.presentation.screens.questions_draw.components.DrawnQuestionsOperations
import com.sweak.diplomaexam.presentation.screens.questions_draw.components.QuestionsDrawPrompt
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun QuestionsDrawScreen(
    questionsDrawViewModel: QuestionsDrawViewModel = hiltViewModel(),
    navController: NavController
) {
    val context: Context = LocalContext.current

    LaunchedEffect(key1 = context) {
        questionsDrawViewModel.questionsConfirmedEvents.collect {
            navController.navigate(Screen.QuestionsAnsweringScreen.route) {
                popUpTo(Screen.QuestionsDrawScreen.route) {
                    inclusive = true
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
            errorMessage = questionsDrawState.errorMessage,
            hasStudentRequestedRedraw = questionsDrawState.hasStudentRequestedRedraw,
            waitingForDecisionFrom = questionsDrawState.waitingForDecisionFrom,
            onDrawQuestionsClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.DrawQuestions)
            },
            onRedrawQuestionsClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.TryRequestQuestionsRedraw)
            },
            onAcceptDrawnQuestions = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.TryAcceptQuestions)
            },
            onAllowQuestionsRedraw = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.AllowRedraw)
            },
            onDisallowQuestionsRedraw = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.TryDisallowRedraw)
            },
            onRetryClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.RetryAfterError)
            }
        )
    } else {
        MediumOrExpandedQuestionsDrawScreen(
            currentUser = questionsDrawState.currentUser,
            otherUser = questionsDrawState.otherUser,
            questions = questionsDrawState.questions,
            isLoadingResponse = questionsDrawState.isLoadingResponse,
            errorMessage = questionsDrawState.errorMessage,
            hasStudentRequestedRedraw = questionsDrawState.hasStudentRequestedRedraw,
            waitingForDecisionFrom = questionsDrawState.waitingForDecisionFrom,
            onDrawQuestionsClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.DrawQuestions)
            },
            onRedrawQuestionsClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.TryRequestQuestionsRedraw)
            },
            onAcceptDrawnQuestions = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.TryAcceptQuestions)
            },
            onAllowQuestionsRedraw = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.AllowRedraw)
            },
            onDisallowQuestionsRedraw = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.TryDisallowRedraw)
            },
            onRetryClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.RetryAfterError)
            }
        )
    }

    if (questionsDrawState.acceptQuestionsDialogVisible) {
        Dialog(
            title = stringResource(R.string.continue_interrogative),
            message = stringResource(R.string.do_you_want_to_accept_questions),
            onDismissRequest = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.HideAcceptQuestionsDialog)
            },
            onPositiveClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.HideAcceptQuestionsDialog)
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.AcceptQuestions)
            },
            positiveButtonText = stringResource(R.string.yes),
            negativeButtonText = stringResource(R.string.no)
        )
    }

    if (questionsDrawState.redrawQuestionsDialogVisible) {
        Dialog(
            title = stringResource(R.string.redraw_interrogative),
            message = stringResource(R.string.do_you_want_to_redraw),
            onDismissRequest = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.HideRequestRedrawDialog)
            },
            onPositiveClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.HideRequestRedrawDialog)
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.RequestQuestionsRedraw)
            },
            positiveButtonText = stringResource(R.string.yes),
            negativeButtonText = stringResource(R.string.no)
        )
    }

    if (questionsDrawState.disallowRedrawDialogVisible) {
        Dialog(
            title = stringResource(R.string.dont_allow_interrogative),
            message = stringResource(R.string.do_you_want_to_disallow_redraw),
            onDismissRequest = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.HideDisallowRedrawDialog)
            },
            onPositiveClick = {
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.HideDisallowRedrawDialog)
                questionsDrawViewModel.onEvent(QuestionsDrawScreenEvent.DisallowRedraw)
            },
            positiveButtonText = stringResource(R.string.yes),
            negativeButtonText = stringResource(R.string.no)
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
    errorMessage: UiText?,
    hasStudentRequestedRedraw: Boolean,
    waitingForDecisionFrom: UserRole,
    onDrawQuestionsClick: () -> Unit,
    onRedrawQuestionsClick: () -> Unit,
    onAcceptDrawnQuestions: () -> Unit,
    onAllowQuestionsRedraw: () -> Unit,
    onDisallowQuestionsRedraw: () -> Unit,
    onRetryClick: () -> Unit
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
            titleText = stringResource(R.string.drawing_questions),
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
            AnimatedContent(
                targetState =
                if (errorMessage != null) AnimatedComponentTargetState.ERROR
                else if (currentUser == null) AnimatedComponentTargetState.LOADING
                else if (questions == null) AnimatedComponentTargetState.QUESTIONS_NOT_DRAWN
                else AnimatedComponentTargetState.QUESTIONS_DRAWN
            ) { targetState ->
                when (targetState) {
                    AnimatedComponentTargetState.QUESTIONS_DRAWN ->
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
                                onRedrawQuestions = onRedrawQuestionsClick,
                                onAllowQuestionsRedraw = onAllowQuestionsRedraw,
                                onDisallowQuestionsRedraw = onDisallowQuestionsRedraw,
                                modifier = Modifier
                                    .padding(
                                        start = MaterialTheme.space.large,
                                        end = MaterialTheme.space.large,
                                        bottom = MaterialTheme.space.large
                                    )
                            )
                        }
                    AnimatedComponentTargetState.QUESTIONS_NOT_DRAWN ->
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
                    AnimatedComponentTargetState.LOADING ->
                        LoadingLayout(
                            modifier = Modifier.padding(
                                start = MaterialTheme.space.large,
                                end = MaterialTheme.space.large,
                                bottom = MaterialTheme.space.large,
                                top = MaterialTheme.space.medium,
                            )
                        )
                    AnimatedComponentTargetState.ERROR ->
                        ErrorLayout(
                            onRetryClick = onRetryClick,
                            text = errorMessage?.asString(),
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
    errorMessage: UiText?,
    hasStudentRequestedRedraw: Boolean,
    waitingForDecisionFrom: UserRole,
    onDrawQuestionsClick: () -> Unit,
    onRedrawQuestionsClick: () -> Unit,
    onAcceptDrawnQuestions: () -> Unit,
    onAllowQuestionsRedraw: () -> Unit,
    onDisallowQuestionsRedraw: () -> Unit,
    onRetryClick: () -> Unit
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
            titleText = stringResource(R.string.drawing_questions),
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
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedContent(
                targetState =
                if (errorMessage != null) AnimatedComponentTargetState.ERROR
                else if (currentUser == null) AnimatedComponentTargetState.LOADING
                else if (questions == null) AnimatedComponentTargetState.QUESTIONS_NOT_DRAWN
                else AnimatedComponentTargetState.QUESTIONS_DRAWN
            ) { targetState ->
                when (targetState) {
                    AnimatedComponentTargetState.QUESTIONS_DRAWN ->
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
                                onRedrawQuestions = onRedrawQuestionsClick,
                                onAllowQuestionsRedraw = onAllowQuestionsRedraw,
                                onDisallowQuestionsRedraw = onDisallowQuestionsRedraw,
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
                    AnimatedComponentTargetState.QUESTIONS_NOT_DRAWN ->
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
                    AnimatedComponentTargetState.LOADING ->
                        LoadingLayout(
                            modifier = Modifier.padding(
                                start = MaterialTheme.space.large,
                                end = MaterialTheme.space.large,
                                bottom = MaterialTheme.space.medium,
                                top = MaterialTheme.space.small
                            )
                        )
                    AnimatedComponentTargetState.ERROR ->
                        ErrorLayout(
                            onRetryClick = onRetryClick,
                            text = errorMessage?.asString(),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(
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

private enum class AnimatedComponentTargetState {
    ERROR, LOADING, QUESTIONS_NOT_DRAWN, QUESTIONS_DRAWN
}
