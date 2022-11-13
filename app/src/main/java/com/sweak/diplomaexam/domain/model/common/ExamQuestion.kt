package com.sweak.diplomaexam.domain.model.common

data class ExamQuestion(
    val id: Int,
    val number: Int,
    val question: String,
    val answer: String?
)
