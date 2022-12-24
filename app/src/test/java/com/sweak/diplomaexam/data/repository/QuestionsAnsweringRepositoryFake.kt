package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.domain.model.common.*
import com.sweak.diplomaexam.domain.model.questions_answering.QuestionsAnsweringState
import com.sweak.diplomaexam.domain.repository.QuestionsAnsweringRepository

class QuestionsAnsweringRepositoryFake : QuestionsAnsweringRepository {

    var isSuccessfulResponse: Boolean = true
    var userRole: UserRole = UserRole.USER_EXAMINER
    var willBeWaitingForStudentUser: Boolean = true
    var willBeWaitingForFinalEvaluation: Boolean = false
    private var waitingForStudentCounter: Int = 0
    private var waitingForFinalEvaluationCounter: Int = 0

    override suspend fun getQuestionsAnsweringState(): Resource<QuestionsAnsweringState> {
        return if (isSuccessfulResponse) {
            Resource.Success(
                QuestionsAnsweringState(
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
                    waitingForStudentCounter++ == 3 && willBeWaitingForStudentUser,
                    waitingForFinalEvaluationCounter++ == 3 && willBeWaitingForFinalEvaluation,
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

    override suspend fun confirmReadinessToAnswer(): Resource<Unit> {
        return if (isSuccessfulResponse) {
            Resource.Success(Unit)
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override suspend fun submitQuestionGrades(questionsToGradesMap: Map<ExamQuestion, Grade>): Resource<Unit> {
        return if (isSuccessfulResponse) {
            Resource.Success(Unit)
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override suspend fun submitAdditionalGrades(
        thesisGrade: Grade,
        thesisPresentationGrade: Grade,
        courseOfStudiesPreciseGradeString: String
    ): Resource<Unit> {
        return if (isSuccessfulResponse) {
            Resource.Success(Unit)
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }
}