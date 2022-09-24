package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.common.DUMMY_IS_STUDENT_READY_TO_ANSWER
import kotlinx.coroutines.delay
import javax.inject.Inject

class ConfirmReadinessToAnswer @Inject constructor() {

    suspend operator fun invoke() {
        delay(1000)
        DUMMY_IS_STUDENT_READY_TO_ANSWER = true
    }
}