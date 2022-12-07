package com.sweak.diplomaexam.data.remote.dto.session

data class SubmitAdditionalGradesRequestDto(
    val sessionId: Int,
    val diplomaGrade: String,
    val presentationGrade: String,
    val studyGrade: String
)