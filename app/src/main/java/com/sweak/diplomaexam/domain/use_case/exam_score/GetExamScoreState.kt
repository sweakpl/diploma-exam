package com.sweak.diplomaexam.domain.use_case.exam_score

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.repository.ExamScoreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExamScoreState @Inject constructor(
    private val repository: ExamScoreRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading())

        delay(500)

        when (val examScoreState = repository.getExamScoreState()) {
            is Resource.Success -> emit(Resource.Success(examScoreState.data))
            else -> emit(Resource.Failure(examScoreState.error!!))
        }
    }
}
