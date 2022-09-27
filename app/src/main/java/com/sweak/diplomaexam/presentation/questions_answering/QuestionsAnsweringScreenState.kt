package com.sweak.diplomaexam.presentation.questions_answering

import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.domain.model.Grade
import com.sweak.diplomaexam.domain.model.User

data class QuestionsAnsweringScreenState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion> = emptyList(),
    val questionNumbersToGradesMap: Map<Int, Grade> = emptyMap(),
    val isLoadingResponse: Boolean = false,
    val isWaitingForStudentReadiness: Boolean = false,
    val isWaitingForFinalEvaluation: Boolean = false,
    val studentPreparationDialogVisible: Boolean = true,
    val cannotSubmitGradesDialogVisible: Boolean = false,
    val submitQuestionGradesDialogVisible: Boolean = false
)
