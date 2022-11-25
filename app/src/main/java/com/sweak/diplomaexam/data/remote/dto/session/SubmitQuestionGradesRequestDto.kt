package com.sweak.diplomaexam.data.remote.dto.session

data class SubmitQuestionGradesRequestDto(
    val sessionId: Int,
    val question1: GradedQuestionDto,
    val question2: GradedQuestionDto,
    val question3: GradedQuestionDto
)
