package com.sweak.diplomaexam.presentation.questions_answering

import com.sweak.diplomaexam.domain.model.Grade

sealed class QuestionsAnsweringScreenEvent {
    object ConfirmReadinessToAnswer : QuestionsAnsweringScreenEvent()
    object HidePreparationDialog : QuestionsAnsweringScreenEvent()
    data class SelectGrade(val questionNumber: Int, val grade: Grade) :
        QuestionsAnsweringScreenEvent()
}
