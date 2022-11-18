package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.repository.QuestionsAnsweringRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubmitQuestionGrades @Inject constructor(
    private val repository: QuestionsAnsweringRepository
) {
    operator fun invoke(grades: List<Grade>) = flow {
        emit(Resource.Loading())

        delay(500)

        when (val submitGradesResponse = repository.submitQuestionGrades(grades)) {
            is Resource.Success -> emit(Resource.Success(Unit))
            else -> emit(Resource.Failure(submitGradesResponse.error!!))
        }
    }
}