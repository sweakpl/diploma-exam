package com.sweak.diplomaexam.presentation.exam_score

import com.sweak.diplomaexam.domain.model.common.Grade

data class ExamScoreScreenState(
    val isLoadingResponse: Boolean = true,
    val finalGrade: Grade? = null,
    val diplomaExamGrade: Grade? = null,
    val thesisGrade: Grade? = null,
    val courseOfStudiesGrade: Grade? = null
)
