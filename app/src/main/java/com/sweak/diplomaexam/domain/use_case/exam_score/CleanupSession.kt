package com.sweak.diplomaexam.domain.use_case.exam_score

import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.domain.*
import com.sweak.diplomaexam.domain.model.common.Grade
import javax.inject.Inject

class CleanupSession @Inject constructor(private val userSessionManager: UserSessionManager) {

    operator fun invoke() {
        userSessionManager.cleanUpSession()

        DUMMY_DRAWN_QUESTIONS = emptyList()
        DUMMY_IS_STUDENT_READY_TO_ANSWER = false
        DUMMY_ARE_QUESTION_GRADES_CONFIRMED = false
        DUMMY_ARE_ADDITIONAL_GRADES_CONFIRMED = false
        DUMMY_DIPLOMA_EXAM_GRADE = Grade.C
        DUMMY_THESIS_PRESENTATION_GRADE = Grade.D
        DUMMY_THESIS_GRADE = Grade.E
        DUMMY_COURSE_OF_STUDIES_GRADE = Grade.D
    }
}