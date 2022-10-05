package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.domain.DUMMY_ARE_ADDITIONAL_GRADES_CONFIRMED
import com.sweak.diplomaexam.domain.DUMMY_COURSE_OF_STUDIES_GRADE
import com.sweak.diplomaexam.domain.DUMMY_THESIS_GRADE
import com.sweak.diplomaexam.domain.model.Grade
import kotlinx.coroutines.delay
import javax.inject.Inject

class SubmitAdditionalGrades @Inject constructor() {

    suspend operator fun invoke(thesisGrade: Grade?, courseOfStudies: Grade?) {
        delay(1000)
        DUMMY_ARE_ADDITIONAL_GRADES_CONFIRMED = true

        if (thesisGrade != null && courseOfStudies != null) {
            DUMMY_THESIS_GRADE = thesisGrade
            DUMMY_COURSE_OF_STUDIES_GRADE = courseOfStudies
        }
    }
}