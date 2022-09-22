package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.common.*
import com.sweak.diplomaexam.domain.model.QuestionsAnsweringState
import com.sweak.diplomaexam.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuestionsAnsweringState @Inject constructor() {

    operator fun invoke() = flow<Resource<QuestionsAnsweringState>> {
        delay(3000)
        emit(
            Resource.Success(
                QuestionsAnsweringState(
                    currentUser = User(DUMMY_USER_ROLE, DUMMY_USER_EMAIL),
                    otherUser = User(DUMMY_OTHER_USER_ROLE, DUMMY_OTHER_USER_EMAIL),
                    questions = DUMMY_DRAWN_QUESTIONS,
                    isWaitingForStudentReadiness = true
                )
            )
        )
    }
}