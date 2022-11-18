package com.sweak.diplomaexam.data.remote.dto.session

data class SubmitAdditionalGradesRequestDto(
    val sessionId: Int,
    val diplomaGrade: Int,
    val presentationGrade: Int,
    val studyGrade: Int
)