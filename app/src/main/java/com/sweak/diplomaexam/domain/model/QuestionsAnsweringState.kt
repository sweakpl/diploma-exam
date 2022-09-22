package com.sweak.diplomaexam.domain.model

data class QuestionsAnsweringState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion> = emptyList(),
    val isWaitingForStudentReadiness: Boolean = false
)