package com.sweak.diplomaexam.presentation.questions_draw

sealed class QuestionsDrawScreenEvent {
    object DrawQuestions: QuestionsDrawScreenEvent()
    object AcceptQuestions: QuestionsDrawScreenEvent()
    object AllowRedraw: QuestionsDrawScreenEvent()
}
