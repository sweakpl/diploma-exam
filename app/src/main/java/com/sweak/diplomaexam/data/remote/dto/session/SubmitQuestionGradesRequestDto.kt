package com.sweak.diplomaexam.data.remote.dto.session

data class SubmitQuestionGradesRequestDto(
    val sessionId: Int,
    val firstQuestionGrade: Int,
    val secondQuestionGrade: Int,
    val thirdQuestionGrade: Int
)
