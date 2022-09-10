package com.sweak.diplomaexam.presentation.questions_draw

import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.domain.model.User

data class QuestionsDrawScreenState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val areQuestionsInDrawingProcess: Boolean = false,
    val questions: List<ExamQuestion>? = null
)
