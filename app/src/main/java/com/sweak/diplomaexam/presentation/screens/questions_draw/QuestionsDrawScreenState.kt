package com.sweak.diplomaexam.presentation.screens.questions_draw

import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.User

data class QuestionsDrawScreenState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion>? = null,
    val isLoadingResponse: Boolean = false,
    val hasStudentRequestedRedraw: Boolean = true,
    val waitingForDecisionFrom: UserRole = UserRole.USER_STUDENT,
    val acceptQuestionsDialogVisible: Boolean = false,
    val disallowRedrawDialogVisible: Boolean = false
)
