package com.sweak.diplomaexam.data.remote.dto.session

data class ExamScoreDto(
    val id: Int,
    val roundedFinalGrade: String,
    val preciseFinalGrade: String,
    val diplomaGrade: String,
    val presentationGrade: String,
    val studyGrade: String
)
