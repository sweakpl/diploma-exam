package com.sweak.diplomaexam.presentation.questions_draw

import com.sweak.diplomaexam.domain.model.User

data class QuestionsDrawState(
    val currentUser: User? = null,
    val otherUser: User? = null
)
