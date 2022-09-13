package com.sweak.diplomaexam.domain.model

import com.sweak.diplomaexam.common.UserRole

data class QuestionsDrawState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion>? = null,
    val hasStudentRequestedRedraw: Boolean = false,
    val waitingForDecisionFrom: UserRole = UserRole.USER_STUDENT,
    val areQuestionsConfirmed: Boolean = false
)
