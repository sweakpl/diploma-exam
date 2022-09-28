package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.common.*
import com.sweak.diplomaexam.domain.model.QuestionsAnsweringState
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.domain.model.UserRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuestionsAnsweringState @Inject constructor() {

    private var state: QuestionsAnsweringState = QuestionsAnsweringState()

    operator fun invoke() = flow<Resource<QuestionsAnsweringState>> {
        delay(3000)

        state = state.copy(
            currentUser = User(DUMMY_USER_ROLE, DUMMY_USER_EMAIL),
            otherUser = User(DUMMY_OTHER_USER_ROLE, DUMMY_OTHER_USER_EMAIL),
            questions = DUMMY_DRAWN_QUESTIONS,
            isWaitingForStudentReadiness = true,
        )
        emit(Resource.Success(state))

        if (DUMMY_USER_ROLE == UserRole.USER_STUDENT) {
            while (true) {
                delay(3000)

                if (DUMMY_IS_STUDENT_READY_TO_ANSWER) {
                    state = state.copy(isWaitingForStudentReadiness = false)
                    emit(Resource.Success(state))
                    break
                }
            }

            delay(10000)

            state = state.copy(isWaitingForFinalEvaluation = true)
            emit(Resource.Success(state))

            delay(10000)

            state = state.copy(isGradingCompleted = true)
            emit(Resource.Success(state))
        } else if (DUMMY_USER_ROLE == UserRole.USER_EXAMINER) {
            delay(10000)

            state = state.copy(isWaitingForStudentReadiness = false)
            emit(Resource.Success(state))

            while (true) {
                delay(3000)

                if (DUMMY_ARE_QUESTION_GRADES_CONFIRMED) {
                    state = state.copy(isWaitingForFinalEvaluation = true)
                    emit(Resource.Success(state))
                    break
                }
            }

            while (true) {
                delay(3000)

                if (DUMMY_ARE_ADDITIONAL_GRADES_CONFIRMED) {
                    state = state.copy(isGradingCompleted = true)
                    emit(Resource.Success(state))
                    break
                }
            }
        }
    }
}