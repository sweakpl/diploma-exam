package com.sweak.diplomaexam.presentation.screens.exam_score

import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.presentation.screens.common.UiText

data class ExamScoreScreenState(
    val isLoadingResponse: Boolean = true,
    val errorMessage: UiText? = null,
    val finalGrade: Grade? = null,
    val diplomaExamGrade: Grade? = null,
    val thesisGrade: Grade? = null,
    val courseOfStudiesGrade: Grade? = null
)
