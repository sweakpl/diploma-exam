package com.sweak.diplomaexam.presentation.screens.exam_score

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.presentation.Screen
import com.sweak.diplomaexam.presentation.screens.common.UiText
import com.sweak.diplomaexam.presentation.screens.common.WindowInfo
import com.sweak.diplomaexam.presentation.screens.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.screens.common.components.ErrorLayout
import com.sweak.diplomaexam.presentation.screens.common.components.LoadingLayout
import com.sweak.diplomaexam.presentation.screens.exam_score.components.FinalGradeInformation
import com.sweak.diplomaexam.presentation.screens.exam_score.components.FinishExamPrompt
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun ExamScoreScreen(
    examScoreViewModel: ExamScoreViewModel = hiltViewModel(),
    navController: NavController
) {
    val context: Context = LocalContext.current

    LaunchedEffect(key1 = context) {
        examScoreViewModel.examFinishedEvents.collect {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.ExamScoreScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    val windowInfo = rememberWindowInfo()
    val examScoreState = examScoreViewModel.state

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactExamScoreScreen(
            isLoadingResponse = examScoreState.isLoadingResponse,
            errorMessage = examScoreState.errorMessage,
            roundedFinalGrade = examScoreState.roundedFinalGrade,
            preciseFinalGrade = examScoreState.preciseFinalGrade,
            diplomaExamGrade = examScoreState.diplomaExamGrade,
            thesisGrade = examScoreState.thesisGrade,
            courseOfStudiesPreciseGrade = examScoreState.courseOfStudiesPreciseGrade,
            onFinishExam = { examScoreViewModel.onEvent(ExamScoreScreenEvent.FinishExam) },
            onRetryClick = { examScoreViewModel.onEvent(ExamScoreScreenEvent.RetryAfterError) }
        )
    } else {
        MediumOrExpandedExamScoreScreen(
            isLoadingResponse = examScoreState.isLoadingResponse,
            errorMessage = examScoreState.errorMessage,
            roundedFinalGrade = examScoreState.roundedFinalGrade,
            preciseFinalGrade = examScoreState.preciseFinalGrade,
            diplomaExamGrade = examScoreState.diplomaExamGrade,
            thesisGrade = examScoreState.thesisGrade,
            courseOfStudiesPreciseGrade = examScoreState.courseOfStudiesPreciseGrade,
            onFinishExam = { examScoreViewModel.onEvent(ExamScoreScreenEvent.FinishExam) },
            onRetryClick = { examScoreViewModel.onEvent(ExamScoreScreenEvent.RetryAfterError) }
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactExamScoreScreen(
    isLoadingResponse: Boolean,
    errorMessage: UiText?,
    roundedFinalGrade: Grade?,
    preciseFinalGrade: Float?,
    diplomaExamGrade: Grade?,
    thesisGrade: Grade?,
    courseOfStudiesPreciseGrade: Float?,
    onFinishExam: () -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                )
            )
    ) {
        Text(
            text = stringResource(R.string.final_score),
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = MaterialTheme.space.large)
        )

        AnimatedContent(
            targetState =
            if (errorMessage != null) AnimatedComponentTargetState.ERROR
            else if (isLoadingResponse) AnimatedComponentTargetState.LOADING
            else AnimatedComponentTargetState.SUMMARY,
            modifier = Modifier.fillMaxSize()
        ) { targetState ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                when (targetState) {
                    AnimatedComponentTargetState.SUMMARY -> {
                        FinalGradeInformation(
                            roundedFinalGrade = roundedFinalGrade!!,
                            preciseFinalGrade = preciseFinalGrade!!,
                            diplomaExamGrade = diplomaExamGrade!!,
                            thesisGrade = thesisGrade!!,
                            courseOfStudiesPreciseGrade = courseOfStudiesPreciseGrade!!,
                            modifier = Modifier
                                .padding(horizontal = MaterialTheme.space.large)
                                .weight(1f)
                        )

                        FinishExamPrompt(
                            onFinishExamClick = onFinishExam,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = MaterialTheme.space.large,
                                    vertical = MaterialTheme.space.large
                                )
                        )
                    }
                    AnimatedComponentTargetState.LOADING -> LoadingLayout()
                    AnimatedComponentTargetState.ERROR ->
                        ErrorLayout(
                            onRetryClick = onRetryClick,
                            text = errorMessage?.asString(),
                            modifier = Modifier.padding(horizontal = MaterialTheme.space.large)
                        )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedExamScoreScreen(
    isLoadingResponse: Boolean,
    errorMessage: UiText?,
    roundedFinalGrade: Grade?,
    preciseFinalGrade: Float?,
    diplomaExamGrade: Grade?,
    thesisGrade: Grade?,
    courseOfStudiesPreciseGrade: Float?,
    onFinishExam: () -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                )
            )
    ) {
        Text(
            text = stringResource(R.string.final_score),
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = MaterialTheme.space.large)
        )

        AnimatedContent(
            targetState =
            if (errorMessage != null) AnimatedComponentTargetState.ERROR
            else if (isLoadingResponse) AnimatedComponentTargetState.LOADING
            else AnimatedComponentTargetState.SUMMARY,
            modifier = Modifier.fillMaxSize()
        ) { targetState ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = MaterialTheme.space.large,
                        vertical = MaterialTheme.space.medium
                    )
            ) {
                when (targetState) {
                    AnimatedComponentTargetState.SUMMARY -> {
                        FinalGradeInformation(
                            roundedFinalGrade = roundedFinalGrade!!,
                            preciseFinalGrade = preciseFinalGrade!!,
                            diplomaExamGrade = diplomaExamGrade!!,
                            thesisGrade = thesisGrade!!,
                            courseOfStudiesPreciseGrade = courseOfStudiesPreciseGrade!!,
                            modifier = Modifier
                                .padding(end = MaterialTheme.space.large)
                                .weight(1f)
                        )

                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(start = MaterialTheme.space.large)
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
                            FinishExamPrompt(onFinishExamClick = onFinishExam)
                        }
                    }
                    AnimatedComponentTargetState.LOADING -> LoadingLayout()
                    AnimatedComponentTargetState.ERROR ->
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
    ERROR, LOADING, SUMMARY
}
