package com.sweak.diplomaexam.presentation.screens.questions_answering

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.presentation.Screen
import com.sweak.diplomaexam.presentation.screens.common.UiText
import com.sweak.diplomaexam.presentation.screens.common.WindowInfo
import com.sweak.diplomaexam.presentation.screens.common.components.*
import com.sweak.diplomaexam.presentation.screens.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.screens.questions_answering.components.*
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun QuestionsAnsweringScreen(
    questionsAnsweringViewModel: QuestionsAnsweringViewModel = hiltViewModel(),
    navController: NavController
) {
    val context: Context = LocalContext.current

    LaunchedEffect(key1 = context) {
        questionsAnsweringViewModel.gradingCompletedEvents.collect {
            navController.navigate(Screen.ExamScoreScreen.route) {
                popUpTo(Screen.QuestionsAnsweringScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    val windowInfo = rememberWindowInfo()
    val questionsAnsweringState = questionsAnsweringViewModel.state

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactQuestionsAnsweringScreen(
            currentUser = questionsAnsweringState.currentUser,
            otherUser = questionsAnsweringState.otherUser,
            questions = questionsAnsweringState.questions,
            questionsToGradesMap = questionsAnsweringState.questionsToGradesMap,
            thesisPresentationGrade = questionsAnsweringState.thesisPresentationGrade,
            thesisGrade = questionsAnsweringState.thesisGrade,
            courseOfStudiesPreciseGradeString =
            questionsAnsweringState.courseOfStudiesPreciseGradeString,
            isLoadingResponse = questionsAnsweringState.isLoadingResponse,
            errorMessage = questionsAnsweringState.errorMessage,
            isWaitingForStudentReadiness = questionsAnsweringState.isWaitingForStudentReadiness,
            isWaitingForFinalEvaluation = questionsAnsweringState.isWaitingForFinalEvaluation,
            onConfirmReadiness = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.ConfirmReadinessToAnswer
                )
            },
            onQuestionGradeSelected = { question, grade ->
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.SelectQuestionGrade(question, grade)
                )
            },
            onThesisPresentationGradeSelected = { grade ->
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.SelectThesisPresentationGrade(grade)
                )
            },
            onThesisGradeSelected = { grade ->
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.SelectThesisGrade(grade)
                )
            },
            onCourseOfStudiesGradeStringChanged = { gradeString ->
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.CourseOfStudiesGradeStringChanged(gradeString)
                )
            },
            onProceedClick = {
                questionsAnsweringViewModel.onEvent(QuestionsAnsweringScreenEvent.ProceedClick)
            },
            onRetryClick = {
                questionsAnsweringViewModel.onEvent(QuestionsAnsweringScreenEvent.RetryAfterError)
            }
        )
    } else {
        MediumOrExpandedQuestionsAnsweringScreen(
            currentUser = questionsAnsweringState.currentUser,
            otherUser = questionsAnsweringState.otherUser,
            questions = questionsAnsweringState.questions,
            questionsToGradesMap = questionsAnsweringState.questionsToGradesMap,
            thesisPresentationGrade = questionsAnsweringState.thesisPresentationGrade,
            thesisGrade = questionsAnsweringState.thesisGrade,
            courseOfStudiesPreciseGradeString =
            questionsAnsweringState.courseOfStudiesPreciseGradeString,
            isLoadingResponse = questionsAnsweringState.isLoadingResponse,
            errorMessage = questionsAnsweringState.errorMessage,
            isWaitingForStudentReadiness = questionsAnsweringState.isWaitingForStudentReadiness,
            isWaitingForFinalEvaluation = questionsAnsweringState.isWaitingForFinalEvaluation,
            onConfirmReadiness = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.ConfirmReadinessToAnswer
                )
            },
            onQuestionGradeSelected = { question, grade ->
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.SelectQuestionGrade(question, grade)
                )
            },
            onThesisPresentationGradeSelected = { grade ->
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.SelectThesisPresentationGrade(grade)
                )
            },
            onThesisGradeSelected = { grade ->
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.SelectThesisGrade(grade)
                )
            },
            onCourseOfStudiesGradeStringChanged = { gradeString ->
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.CourseOfStudiesGradeStringChanged(gradeString)
                )
            },
            onProceedClick = {
                questionsAnsweringViewModel.onEvent(QuestionsAnsweringScreenEvent.ProceedClick)
            },
            onRetryClick = {
                questionsAnsweringViewModel.onEvent(QuestionsAnsweringScreenEvent.RetryAfterError)
            }
        )
    }

    if (questionsAnsweringState.currentUser?.role == UserRole.USER_STUDENT &&
        questionsAnsweringState.studentPreparationDialogVisible
    ) {
        Dialog(
            title = stringResource(R.string.prepare),
            message = stringResource(R.string.prepare_and_confirm_readiness),
            onDismissRequest = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HidePreparationDialog
                )
            },
            onlyPositiveButton = true,
            onPositiveClick = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HidePreparationDialog
                )
            },
            positiveButtonText = stringResource(R.string.ok)
        )
    }

    if (questionsAnsweringState.cannotSubmitGradesDialogVisible) {
        Dialog(
            title = stringResource(R.string.cannot_continue),
            message = stringResource(R.string.grades_missing_cannot_continue),
            onDismissRequest = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideCannotSubmitGradesDialog
                )
            },
            onlyPositiveButton = true,
            onPositiveClick = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideCannotSubmitGradesDialog
                )
            },
            positiveButtonText = stringResource(R.string.ok)
        )
    }

    if (questionsAnsweringState.submitQuestionGradesDialogVisible) {
        Dialog(
            title = stringResource(R.string.continue_interrogative),
            message = stringResource(R.string.do_you_want_to_submit_question_grades),
            onDismissRequest = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideSubmitQuestionGradesDialog
                )
            },
            onPositiveClick = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideSubmitQuestionGradesDialog
                )
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.SubmitQuestionGrades
                )
            },
            positiveButtonText = stringResource(R.string.yes),
            onNegativeClick = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideSubmitQuestionGradesDialog
                )
            },
            negativeButtonText = stringResource(R.string.no)
        )
    }

    if (questionsAnsweringState.submitAdditionalGradesDialogVisible) {
        Dialog(
            title = stringResource(R.string.continue_interrogative),
            message = stringResource(R.string.do_you_want_to_submit_additional_grades),
            onDismissRequest = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideSubmitAdditionalGradesDialog
                )
            },
            onPositiveClick = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideSubmitAdditionalGradesDialog
                )
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.SubmitAdditionalGrades
                )
            },
            positiveButtonText = stringResource(R.string.yes),
            onNegativeClick = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideSubmitAdditionalGradesDialog
                )
            },
            negativeButtonText = stringResource(R.string.no)
        )
    }

    if (questionsAnsweringState.wrongCourseOfStudiesGradeDialogVisible) {
        Dialog(
            title = stringResource(R.string.cannot_continue),
            message = stringResource(R.string.wrong_course_of_studies_grade),
            onDismissRequest = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideWrongCourseOfStudiesGradeDialogVisible
                )
            },
            onlyPositiveButton = true,
            onPositiveClick = {
                questionsAnsweringViewModel.onEvent(
                    QuestionsAnsweringScreenEvent.HideWrongCourseOfStudiesGradeDialogVisible
                )
            },
            positiveButtonText = stringResource(R.string.ok)
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun CompactQuestionsAnsweringScreen(
    currentUser: User?,
    otherUser: User?,
    questions: List<ExamQuestion>,
    questionsToGradesMap: Map<ExamQuestion, Grade>,
    thesisPresentationGrade: Grade?,
    thesisGrade: Grade?,
    courseOfStudiesPreciseGradeString: String?,
    isLoadingResponse: Boolean,
    errorMessage: UiText?,
    isWaitingForStudentReadiness: Boolean,
    isWaitingForFinalEvaluation: Boolean,
    onConfirmReadiness: () -> Unit,
    onQuestionGradeSelected: (ExamQuestion, Grade) -> Unit,
    onThesisPresentationGradeSelected: (Grade) -> Unit,
    onThesisGradeSelected: (Grade) -> Unit,
    onCourseOfStudiesGradeStringChanged: (String) -> Unit,
    onProceedClick: () -> Unit,
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
            titleText = stringResource(
                if (currentUser?.role == UserRole.USER_EXAMINER) R.string.grading
                else R.string.answering_questions
            ),
            displayMode = HeaderDisplayMode.COMPACT,
            usersInSession = usersInSession,
            proceedButtonEnabled = currentUser != null && currentUser.role == UserRole.USER_EXAMINER,
            onProceedClick = onProceedClick,
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
            targetState =
            if (errorMessage != null) AnimatedComponentTargetState.ERROR
            else if (currentUser == null) AnimatedComponentTargetState.LOADING
            else if (!isWaitingForFinalEvaluation) AnimatedComponentTargetState.ANSWERING_QUESTIONS
            else AnimatedComponentTargetState.SUMMARIZING_EXAM,
            modifier = Modifier.padding(all = MaterialTheme.space.large)
        ) { targetState ->
            when (targetState) {
                AnimatedComponentTargetState.ANSWERING_QUESTIONS ->
                    if (currentUser!!.role == UserRole.USER_STUDENT) {
                        StudentQuestionsPanel(
                            questions = questions,
                            displayMode = StudentQuestionsPanelDisplayMode.COMPACT,
                            isLoadingResponse = isLoadingResponse,
                            isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                            onConfirmReadiness = onConfirmReadiness
                        )
                    } else {
                        ExaminerQuestionsPanel(
                            questions = questions,
                            questionsToGradesMap = questionsToGradesMap,
                            displayMode = ExaminerQuestionsPanelDisplayMode.COMPACT,
                            isLoadingResponse = isLoadingResponse,
                            isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                            onQuestionGradeSelected = onQuestionGradeSelected
                        )
                    }
                AnimatedComponentTargetState.SUMMARIZING_EXAM ->
                    if (currentUser!!.role == UserRole.USER_STUDENT) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LoadingLayout(
                                text = stringResource(R.string.examination_board_summarizing_exam)
                            )
                        }
                    } else {
                        ExaminerAdditionalGradesPanel(
                            displayMode = ExaminerAdditionalGradesPanelDisplayMode.COMPACT,
                            thesisPresentationGrade = thesisPresentationGrade,
                            thesisGrade = thesisGrade,
                            courseOfStudiesGradeString = courseOfStudiesPreciseGradeString,
                            isLoadingResponse = isLoadingResponse,
                            onThesisPresentationGradeSelected = onThesisPresentationGradeSelected,
                            onThesisGradeSelected = onThesisGradeSelected,
                            onCourseOfStudiesGradeStringChanged = onCourseOfStudiesGradeStringChanged
                        )
                    }
                AnimatedComponentTargetState.LOADING ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LoadingLayout()
                    }
                AnimatedComponentTargetState.ERROR ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        ErrorLayout(
                            onRetryClick = onRetryClick,
                            text = errorMessage?.asString()
                        )
                    }
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedQuestionsAnsweringScreen(
    currentUser: User?,
    otherUser: User?,
    questions: List<ExamQuestion>,
    questionsToGradesMap: Map<ExamQuestion, Grade>,
    thesisPresentationGrade: Grade?,
    thesisGrade: Grade?,
    courseOfStudiesPreciseGradeString: String?,
    isLoadingResponse: Boolean,
    errorMessage: UiText?,
    isWaitingForStudentReadiness: Boolean,
    isWaitingForFinalEvaluation: Boolean,
    onConfirmReadiness: () -> Unit,
    onQuestionGradeSelected: (ExamQuestion, Grade) -> Unit,
    onThesisPresentationGradeSelected: (Grade) -> Unit,
    onThesisGradeSelected: (Grade) -> Unit,
    onCourseOfStudiesGradeStringChanged: (String) -> Unit,
    onProceedClick: () -> Unit,
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
            titleText = stringResource(R.string.answering_questions),
            displayMode = HeaderDisplayMode.MEDIUM_OR_EXPANDED,
            usersInSession = usersInSession,
            proceedButtonEnabled = currentUser != null && currentUser.role == UserRole.USER_EXAMINER,
            onProceedClick = onProceedClick,
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
            targetState =
            if (errorMessage != null) AnimatedComponentTargetState.ERROR
            else if (currentUser == null) AnimatedComponentTargetState.LOADING
            else if (!isWaitingForFinalEvaluation) AnimatedComponentTargetState.ANSWERING_QUESTIONS
            else AnimatedComponentTargetState.SUMMARIZING_EXAM,
            modifier = Modifier.padding(all = MaterialTheme.space.large)
        ) { targetState ->
            when (targetState) {
                AnimatedComponentTargetState.ANSWERING_QUESTIONS ->
                    if (currentUser!!.role == UserRole.USER_STUDENT) {
                        StudentQuestionsPanel(
                            questions = questions,
                            displayMode = StudentQuestionsPanelDisplayMode.MEDIUM_OR_EXPANDED,
                            isLoadingResponse = isLoadingResponse,
                            isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                            onConfirmReadiness = onConfirmReadiness
                        )
                    } else {
                        ExaminerQuestionsPanel(
                            questions = questions,
                            questionsToGradesMap = questionsToGradesMap,
                            displayMode = ExaminerQuestionsPanelDisplayMode.MEDIUM_OR_EXPANDED,
                            isLoadingResponse = isLoadingResponse,
                            isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                            onQuestionGradeSelected = onQuestionGradeSelected
                        )
                    }
                AnimatedComponentTargetState.SUMMARIZING_EXAM ->
                    if (currentUser!!.role == UserRole.USER_STUDENT) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LoadingLayout(
                                text = stringResource(R.string.examination_board_summarizing_exam)
                            )
                        }
                    } else {
                        ExaminerAdditionalGradesPanel(
                            displayMode = ExaminerAdditionalGradesPanelDisplayMode.MEDIUM_OR_EXPANDED,
                            thesisPresentationGrade = thesisPresentationGrade,
                            thesisGrade = thesisGrade,
                            courseOfStudiesGradeString = courseOfStudiesPreciseGradeString,
                            isLoadingResponse = isLoadingResponse,
                            onThesisPresentationGradeSelected = onThesisPresentationGradeSelected,
                            onThesisGradeSelected = onThesisGradeSelected,
                            onCourseOfStudiesGradeStringChanged = onCourseOfStudiesGradeStringChanged
                        )
                    }
                AnimatedComponentTargetState.LOADING ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LoadingLayout()
                    }
                AnimatedComponentTargetState.ERROR ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        ErrorLayout(
                            onRetryClick = onRetryClick,
                            text = errorMessage?.asString(),
                            modifier = Modifier.fillMaxWidth(0.5f)
                        )
                    }
            }
        }
    }
}

private enum class AnimatedComponentTargetState {
    ERROR, LOADING, ANSWERING_QUESTIONS, SUMMARIZING_EXAM
}
