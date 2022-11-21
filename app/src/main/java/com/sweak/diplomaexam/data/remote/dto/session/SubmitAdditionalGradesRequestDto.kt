package com.sweak.diplomaexam.data.remote.dto.session

data class SubmitAdditionalGradesRequestDto(
    val sessionId: Int,
    val diplomaGrade: Float,
    val presentationGrade: Float,
    val studyGrade: Float
)