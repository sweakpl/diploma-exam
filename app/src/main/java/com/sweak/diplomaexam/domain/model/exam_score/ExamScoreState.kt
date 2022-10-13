package com.sweak.diplomaexam.domain.model.exam_score

import com.sweak.diplomaexam.domain.model.common.Grade

data class ExamScoreState(
    val finalGrade: Grade,
    val diplomaExamGrade: Grade,
    val thesisGrade: Grade,
    val courseOfStudiesGrade: Grade
)
