package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.domain.DUMMY_ARE_QUESTION_GRADES_CONFIRMED
import com.sweak.diplomaexam.domain.DUMMY_DIPLOMA_EXAM_GRADE
import com.sweak.diplomaexam.domain.model.common.Grade
import kotlinx.coroutines.delay
import java.math.RoundingMode
import javax.inject.Inject

class SubmitQuestionGrades @Inject constructor() {

    suspend operator fun invoke(grades: List<Grade>) {
        delay(1000)
        DUMMY_ARE_QUESTION_GRADES_CONFIRMED = true

        if (grades.isNotEmpty()) {
            DUMMY_DIPLOMA_EXAM_GRADE =
                Grade.values()[
                        (grades.sumOf { it.ordinal } / (grades.size * 1f))
                            .toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toInt()
                ]
        }
    }
}