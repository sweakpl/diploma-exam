package com.sweak.diplomaexam.domain.model

data class QuestionsDrawState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion>? = null
)
