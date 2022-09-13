package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.common.DUMMY_HAVE_QUESTIONS_BEEN_DRAWN
import com.sweak.diplomaexam.common.DUMMY_HAS_STUDENT_REQUESTED_REDRAW
import kotlinx.coroutines.delay
import javax.inject.Inject

class DrawNewQuestions @Inject constructor() {

    suspend operator fun invoke() {
        delay(1000)
        if (DUMMY_HAVE_QUESTIONS_BEEN_DRAWN) {
            DUMMY_HAS_STUDENT_REQUESTED_REDRAW = true
        } else {
            DUMMY_HAVE_QUESTIONS_BEEN_DRAWN = true
        }
    }
}