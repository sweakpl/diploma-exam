package com.sweak.diplomaexam.presentation.exam_score

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.Grade
import com.sweak.diplomaexam.presentation.Screen
import com.sweak.diplomaexam.presentation.common.WindowInfo
import com.sweak.diplomaexam.presentation.common.rememberWindowInfo
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.components.ThickWhiteButton
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
            finalGrade = examScoreState.finalGrade,
            diplomaExamGrade = examScoreState.diplomaExamGrade,
            thesisGrade = examScoreState.thesisGrade,
            courseOfStudiesGrade = examScoreState.courseOfStudiesGrade,
            onFinishExam = { examScoreViewModel.onEvent(ExamScoreScreenEvent.FinishExam) }
        )
    } else {
        MediumOrExpandedExamScoreScreen(
            isLoadingResponse = examScoreState.isLoadingResponse,
            finalGrade = examScoreState.finalGrade,
            diplomaExamGrade = examScoreState.diplomaExamGrade,
            thesisGrade = examScoreState.thesisGrade,
            courseOfStudiesGrade = examScoreState.courseOfStudiesGrade,
            onFinishExam = { examScoreViewModel.onEvent(ExamScoreScreenEvent.FinishExam) }
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CompactExamScoreScreen(
    isLoadingResponse: Boolean,
    finalGrade: Grade?,
    diplomaExamGrade: Grade?,
    thesisGrade: Grade?,
    courseOfStudiesGrade: Grade?,
    onFinishExam: () -> Unit
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
            targetState = isLoadingResponse,
            modifier = Modifier.fillMaxSize()
        ) { targetState ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                if (!targetState &&
                    finalGrade != null &&
                    diplomaExamGrade != null &&
                    thesisGrade != null &&
                    courseOfStudiesGrade != null
                ) {
                    FinalGradeInformation(
                        finalGrade = finalGrade,
                        diplomaExamGrade = diplomaExamGrade,
                        thesisGrade = thesisGrade,
                        courseOfStudiesGrade = courseOfStudiesGrade,
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
                } else {
                    LoadingLayout()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MediumOrExpandedExamScoreScreen(
    isLoadingResponse: Boolean,
    finalGrade: Grade?,
    diplomaExamGrade: Grade?,
    thesisGrade: Grade?,
    courseOfStudiesGrade: Grade?,
    onFinishExam: () -> Unit
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
            targetState = isLoadingResponse,
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
                if (!targetState &&
                    finalGrade != null &&
                    diplomaExamGrade != null &&
                    thesisGrade != null &&
                    courseOfStudiesGrade != null
                ) {
                    FinalGradeInformation(
                        finalGrade = finalGrade,
                        diplomaExamGrade = diplomaExamGrade,
                        thesisGrade = thesisGrade,
                        courseOfStudiesGrade = courseOfStudiesGrade,
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
                } else {
                    LoadingLayout()
                }
            }
        }
    }
}

@Composable
fun FinalGradeInformation(
    finalGrade: Grade,
    diplomaExamGrade: Grade,
    thesisGrade: Grade,
    courseOfStudiesGrade: Grade,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        GradeProgressBar(
            grade = finalGrade,
            size = GradeProgressBarSize.LARGE,
            modifier = Modifier.padding(bottom = MaterialTheme.space.large)
        )

        Text(
            text = stringResource(R.string.final_score_calculation_description),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.space.medium)
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ComponentGradeRow(
                text = stringResource(R.string.diploma_exam),
                grade = diplomaExamGrade,
                modifier = Modifier.fillMaxWidth()
            )

            ComponentGradeRow(
                text = stringResource(R.string.thesis),
                grade = thesisGrade,
                modifier = Modifier
                    .padding(vertical = MaterialTheme.space.medium)
                    .fillMaxWidth()
            )

            ComponentGradeRow(
                text = stringResource(R.string.course_of_studies),
                grade = courseOfStudiesGrade,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun GradeProgressBar(
    grade: Grade,
    size: GradeProgressBarSize,
    modifier: Modifier = Modifier
) {
    val progress = remember { Animatable(0f) }
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        progress.animateTo(
            targetValue = progressFromGrade(grade),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
    }

    Box(modifier = modifier) {
        CircularProgressIndicator(
            progress = progress.value,
            color = MaterialTheme.colors.onPrimary,
            strokeWidth =
            if (size == GradeProgressBarSize.LARGE) MaterialTheme.space.medium
            else MaterialTheme.space.small,
            modifier = Modifier
                .size(size = if (size == GradeProgressBarSize.LARGE) 192.dp else 64.dp)
        )

        Text(
            text = grade.stringRepresentation.replace(' ', '\n'),
            style =
            if (size == GradeProgressBarSize.LARGE) MaterialTheme.typography.h1
            else MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ComponentGradeRow(
    text: String,
    grade: Grade,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h2,
            modifier = Modifier
                .padding(end = MaterialTheme.space.small)
                .weight(1f)
        )

        GradeProgressBar(
            grade = grade,
            size = GradeProgressBarSize.SMALL
        )
    }
}

enum class GradeProgressBarSize {
    SMALL, LARGE
}

@Composable
fun FinishExamPrompt(
    onFinishExamClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.thank_you_for_participation),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = MaterialTheme.space.medium)
        )

        ThickWhiteButton(
            text = stringResource(R.string.finish_exam),
            onClick = onFinishExamClick
        )
    }
}

private fun progressFromGrade(grade: Grade): Float =
    1 - ((grade.ordinal.run { if (this == 5) 6 else this }) / 12f)