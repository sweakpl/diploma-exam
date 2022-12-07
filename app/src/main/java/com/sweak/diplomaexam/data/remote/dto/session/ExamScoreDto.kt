package com.sweak.diplomaexam.data.remote.dto.session

data class ExamScoreDto(
    val id: Int,
    val finalGrade: String,
    val diplomaGrade: String,
    val presentationGrade: String,
    val studyGrade: String
)
