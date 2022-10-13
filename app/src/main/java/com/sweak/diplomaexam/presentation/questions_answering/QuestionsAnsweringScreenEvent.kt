package com.sweak.diplomaexam.presentation.questions_answering

import com.sweak.diplomaexam.domain.model.common.Grade

sealed class QuestionsAnsweringScreenEvent {
    object ConfirmReadinessToAnswer : QuestionsAnsweringScreenEvent()
    object HidePreparationDialog : QuestionsAnsweringScreenEvent()
    data class SelectQuestionGrade(val questionNumber: Int, val grade: Grade) :
        QuestionsAnsweringScreenEvent()
    object ProceedClick : QuestionsAnsweringScreenEvent()
    object SubmitQuestionGrades : QuestionsAnsweringScreenEvent()
    object HideCannotSubmitGradesDialog : QuestionsAnsweringScreenEvent()
    object HideSubmitQuestionGradesDialog : QuestionsAnsweringScreenEvent()
    data class SelectThesisGrade(val grade: Grade) : QuestionsAnsweringScreenEvent()
    data class SelectCourseOfStudiesGrade(val grade: Grade) : QuestionsAnsweringScreenEvent()
    object SubmitAdditionalGrades : QuestionsAnsweringScreenEvent()
    object HideSubmitAdditionalGradesDialog : QuestionsAnsweringScreenEvent()
}
