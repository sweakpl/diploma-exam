package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.repository.QuestionsDrawRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RequestQuestionsRedraw @Inject constructor(
    private val repository: QuestionsDrawRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading())

        delay(500)

        when (val requestRedrawResponse = repository.requestQuestionsRedraw()) {
            is Resource.Success -> emit(Resource.Success(Unit))
            else -> emit(Resource.Failure(requestRedrawResponse.error!!))
        }
    }
}