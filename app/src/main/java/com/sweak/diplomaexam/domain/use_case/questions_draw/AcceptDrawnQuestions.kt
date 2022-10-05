package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.domain.DUMMY_ARE_QUESTIONS_CONFIRMED
import kotlinx.coroutines.delay
import javax.inject.Inject

class AcceptDrawnQuestions @Inject constructor() {

    suspend operator fun invoke() {
        delay(1000)
        DUMMY_ARE_QUESTIONS_CONFIRMED = true
    }
}