package com.sweak.diplomaexam.data.remote.dto.session

data class ExamScoreDto(
    val id: Int,
    val finalGrade: Float,
    val diplomaGrade: Float,
    val presentationGrade: Float,
    val studyGrade: Float,
    val firstQuestionGrade: Float,
    val secondQuestionGrade: Float,
    val thirdQuestionGrade: Float
)
