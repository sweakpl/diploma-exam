package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.common.DUMMY_GLOBAL_HAVE_QUESTIONS_BEEN_DRAWN
import kotlinx.coroutines.delay
import javax.inject.Inject

class DrawQuestions @Inject constructor() {

    suspend operator fun invoke() {
        delay(1000)
        DUMMY_GLOBAL_HAVE_QUESTIONS_BEEN_DRAWN = true
    }
}