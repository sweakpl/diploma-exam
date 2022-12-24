package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.exam_score.ExamScoreState
import com.sweak.diplomaexam.domain.repository.ExamScoreRepository

class ExamScoreRepositoryFake : ExamScoreRepository {

    var isSuccessfulResponse: Boolean = true

    override suspend fun getExamScoreState(): Resource<ExamScoreState> {
        return if (isSuccessfulResponse) {
            Resource.Success(
                ExamScoreState(
                    Grade.C,
                    4.243f,
                    Grade.B,
                    Grade.A,
                    3.769f
                )
            )
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override fun finishExam() {
        /* no-op */
    }
}