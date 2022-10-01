package com.sweak.diplomaexam.domain.model

data class ExamScoreState(
    val finalGrade: Grade,
    val diplomaExamGrade: Grade,
    val thesisGrade: Grade,
    val courseOfStudiesGrade: Grade
)
