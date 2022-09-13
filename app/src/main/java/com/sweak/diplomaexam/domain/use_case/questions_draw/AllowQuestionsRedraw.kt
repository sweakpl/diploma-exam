package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.common.DUMMY_HAS_EXAMINER_ALLOWED_REDRAW
import kotlinx.coroutines.delay
import javax.inject.Inject

class AllowQuestionsRedraw @Inject constructor() {

    suspend operator fun invoke() {
        delay(1000)
        DUMMY_HAS_EXAMINER_ALLOWED_REDRAW = true
    }
}