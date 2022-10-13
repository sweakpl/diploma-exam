package com.sweak.diplomaexam.presentation.questions_answering.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun ExaminerAdditionalGradesPanel(
    displayMode: ExaminerAdditionalGradesPanelDisplayMode,
    thesisGrade: Grade?,
    courseOfStudiesGrade: Grade?,
    isLoadingResponse: Boolean,
    onThesisGradeSelected: (Grade) -> Unit,
    onCourseOfStudiesGradeSelected: (Grade) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(targetState = isLoadingResponse) { targetState ->
        if (!targetState) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.additional_grades_prompt),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(
                        bottom =
                        if (displayMode == ExaminerAdditionalGradesPanelDisplayMode.COMPACT)
                            MaterialTheme.space.large
                        else
                            MaterialTheme.space.medium
                    )
                )

                if (displayMode == ExaminerAdditionalGradesPanelDisplayMode.COMPACT) {
                    GradeCard(
                        gradeCardOrientation = GradeCardOrientation.VERTICAL,
                        text = stringResource(R.string.thesis_presentation_grade),
                        grade = thesisGrade,
                        onGradeSelected = onThesisGradeSelected,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.space.large)
                    )

                    GradeCard(
                        gradeCardOrientation = GradeCardOrientation.VERTICAL,
                        text = stringResource(R.string.course_of_studies_grade),
                        grade = courseOfStudiesGrade,
                        onGradeSelected = onCourseOfStudiesGradeSelected,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (displayMode == ExaminerAdditionalGradesPanelDisplayMode.MEDIUM_OR_EXPANDED) {
                    Row {
                        GradeCard(
                            gradeCardOrientation = GradeCardOrientation.VERTICAL,
                            text = stringResource(R.string.thesis_presentation_grade),
                            grade = thesisGrade,
                            onGradeSelected = onThesisGradeSelected,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(MaterialTheme.space.large))

                        GradeCard(
                            gradeCardOrientation = GradeCardOrientation.VERTICAL,
                            text = stringResource(R.string.course_of_studies_grade),
                            grade = courseOfStudiesGrade,
                            onGradeSelected = onCourseOfStudiesGradeSelected,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                LoadingLayout()
            }
        }
    }
}

enum class ExaminerAdditionalGradesPanelDisplayMode {
    COMPACT, MEDIUM_OR_EXPANDED
}