package com.sweak.diplomaexam.domain.model.exam_score

import com.sweak.diplomaexam.domain.model.common.Grade

data class ExamScoreState(
    val roundedFinalGrade: Grade,
    val preciseFinalGrade: Float,
    val diplomaExamGrade: Grade,
    val thesisGrade: Grade,
    val courseOfStudiesPreciseGrade: Float
)
