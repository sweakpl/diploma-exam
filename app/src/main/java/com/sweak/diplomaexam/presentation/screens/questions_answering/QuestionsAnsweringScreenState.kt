package com.sweak.diplomaexam.presentation.screens.questions_answering

import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.common.User

data class QuestionsAnsweringScreenState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion> = emptyList(),
    val questionNumbersToGradesMap: Map<Int, Grade> = emptyMap(),
    val thesisPresentationGrade: Grade? = null,
    val thesisGrade: Grade? = null,
    val courseOfStudiesGrade: Grade? = null,
    val isLoadingResponse: Boolean = false,
    val isWaitingForStudentReadiness: Boolean = false,
    val isWaitingForFinalEvaluation: Boolean = false,
    val studentPreparationDialogVisible: Boolean = true,
    val cannotSubmitGradesDialogVisible: Boolean = false,
    val submitQuestionGradesDialogVisible: Boolean = false,
    val submitAdditionalGradesDialogVisible: Boolean = false
)
