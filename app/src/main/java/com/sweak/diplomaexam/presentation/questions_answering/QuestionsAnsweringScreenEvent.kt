package com.sweak.diplomaexam.presentation.questions_answering

sealed class QuestionsAnsweringScreenEvent {
    object ConfirmReadinessToAnswer : QuestionsAnsweringScreenEvent()
    object HidePreparationDialog: QuestionsAnsweringScreenEvent()
}
