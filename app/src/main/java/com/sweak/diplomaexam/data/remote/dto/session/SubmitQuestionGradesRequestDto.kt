package com.sweak.diplomaexam.data.remote.dto.session

data class SubmitQuestionGradesRequestDto(
    val sessionId: Int,
    val firstQuestionGrade: Float,
    val secondQuestionGrade: Float,
    val thirdQuestionGrade: Float
)
