package com.sweak.diplomaexam.domain.use_case.lobby

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.repository.LobbyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartExamSession @Inject constructor(
    private val repository: LobbyRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading())

        delay(500)

        when (val confirmedStartedSession = repository.startExaminationSession()) {
            is Resource.Success -> emit(Resource.Success(Unit))
            else -> emit(Resource.Failure(confirmedStartedSession.error!!))
        }
    }
}