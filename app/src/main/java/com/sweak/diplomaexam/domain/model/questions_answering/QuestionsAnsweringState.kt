package com.sweak.diplomaexam.domain.model.questions_answering

import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.User

data class QuestionsAnsweringState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion> = emptyList(),
    val isWaitingForStudentReadiness: Boolean = false,
    val isWaitingForFinalEvaluation: Boolean = false,
    val isGradingCompleted: Boolean = false
)