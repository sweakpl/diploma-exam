package com.sweak.diplomaexam.domain.model

data class QuestionsAnsweringState(
    val currentUser: User? = null,
    val otherUser: User? = null,
    val questions: List<ExamQuestion> = emptyList(),
    val questionNumbersToGradesMap: Map<Int, Grade> = emptyMap(),
    val thesisGrade: Grade? = null,
    val courseOfStudiesGrade: Grade? = null,
    val isWaitingForStudentReadiness: Boolean = false,
    val isWaitingForFinalEvaluation: Boolean = false,
    val isGradingCompleted: Boolean = false
)