package com.sweak.diplomaexam.data.remote.dto.session

data class ExamQuestionDto(
    val answer: String,
    val category: String,
    val id: Int,
    val question: String
)
