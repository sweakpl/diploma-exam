package com.sweak.diplomaexam.presentation.screens.questions_draw

sealed class QuestionsDrawScreenEvent {
    object DrawQuestions: QuestionsDrawScreenEvent()
    object RequestQuestionsRedraw : QuestionsDrawScreenEvent()
    object AcceptQuestions: QuestionsDrawScreenEvent()
    object TryAcceptQuestions: QuestionsDrawScreenEvent()
    object AllowRedraw: QuestionsDrawScreenEvent()
    object DisallowRedraw: QuestionsDrawScreenEvent()
    object TryDisallowRedraw: QuestionsDrawScreenEvent()
    object HideAcceptQuestionsDialog: QuestionsDrawScreenEvent()
    object HideDisallowRedrawDialog: QuestionsDrawScreenEvent()
    object RetryAfterError : QuestionsDrawScreenEvent()
}
