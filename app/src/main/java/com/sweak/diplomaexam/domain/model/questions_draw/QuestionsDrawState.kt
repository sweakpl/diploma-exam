package com.sweak.diplomaexam.domain.model.questions_draw

import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.domain.model.common.UserRole

data class QuestionsDrawState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion>? = null,
    val hasStudentRequestedRedraw: Boolean = false,
    val waitingForDecisionFrom: UserRole = UserRole.USER_STUDENT,
    val areQuestionsConfirmed: Boolean = false
)
