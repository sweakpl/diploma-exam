package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.domain.model.common.*
import com.sweak.diplomaexam.domain.model.questions_draw.QuestionsDrawState
import com.sweak.diplomaexam.domain.repository.QuestionsDrawRepository

class QuestionsDrawRepositoryFake : QuestionsDrawRepository {

    var isSuccessfulResponse: Boolean = true
    var userRole: UserRole = UserRole.USER_EXAMINER
    var willBeWaitingForCurrentUser: Boolean = false
    private var waitingForDecisionCounter: Int = 0
    private var failureCounter: Int = 0

    override suspend fun getQuestionsDrawState(): Resource<QuestionsDrawState> {
        return if (failureCounter++ != 3 || isSuccessfulResponse) {
            Resource.Success(
                QuestionsDrawState(
                    User(userRole, "test.email1@mail.com"),
                    User(
                        if (userRole == UserRole.USER_STUDENT) UserRole.USER_EXAMINER
                        else UserRole.USER_STUDENT,
                        "test.email2@mail.com"
                    ),
                    listOf(
                        ExamQuestion(12, 1, "Questions 1", "Answer 1"),
                        ExamQuestion(32, 2, "Questions 2", "Answer 2"),
                        ExamQuestion(27, 3, "Questions 3", "Answer 3")
                    ),
                    false,
                    if (waitingForDecisionCounter++ == 3 && willBeWaitingForCurrentUser) userRole
                    else if (userRole == UserRole.USER_STUDENT) UserRole.USER_EXAMINER
                    else UserRole.USER_STUDENT,
                    false
                )
            )
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override suspend fun getQuestions(): Resource<List<ExamQuestion>> {
        return if (isSuccessfulResponse) {
            Resource.Success(
                listOf(
                    ExamQuestion(12, 1, "Questions 1", "Answer 1"),
                    ExamQuestion(32, 2, "Questions 2", "Answer 2"),
                    ExamQuestion(27, 3, "Questions 3", "Answer 3")
                )
            )
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override suspend fun requestQuestionsRedraw(): Resource<Unit> {
        return if (isSuccessfulResponse) {
            Resource.Success(Unit)
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override suspend fun redrawQuestions(): Resource<Unit> {
        return if (isSuccessfulResponse) {
            Resource.Success(Unit)
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override suspend fun acceptDrawnQuestions(): Resource<Unit> {
        return if (isSuccessfulResponse) {
            Resource.Success(Unit)
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }
}