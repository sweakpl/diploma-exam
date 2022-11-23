package com.sweak.diplomaexam.presentation.screens.exam_score.components

import androidx.compose.animation.core.Animatable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.presentation.ui.theme.space

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

enum class GradeProgressBarSize {
    SMALL, LARGE
}

private fun progressFromGrade(grade: Grade): Float =
    1 - ((grade.ordinal.run { if (this == 5) 6 else this }) / 12f)