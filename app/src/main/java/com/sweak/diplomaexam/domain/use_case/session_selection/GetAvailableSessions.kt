package com.sweak.diplomaexam.domain.use_case.session_selection

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.repository.SessionSelectionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAvailableSessions @Inject constructor(
    private val repository: SessionSelectionRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading())

        delay(500)

        when (val availableSessions = repository.getAvailableSessions()) {
            is Resource.Success -> emit(Resource.Success(availableSessions.data))
            else -> emit(Resource.Failure(availableSessions.error!!))
        }
    }
}