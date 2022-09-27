package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.common.DUMMY_ARE_QUESTION_GRADES_CONFIRMED
import kotlinx.coroutines.delay
import javax.inject.Inject

class SubmitQuestionGrades @Inject constructor() {

    suspend operator fun invoke() {
        delay(1000)
        DUMMY_ARE_QUESTION_GRADES_CONFIRMED = true
    }
}