package com.sweak.diplomaexam.domain.use_case.session_selection

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.domain.repository.SessionSelectionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SelectExaminationSession @Inject constructor(
    private val repository: SessionSelectionRepository
) {
    operator fun invoke(selectedSession: AvailableSession) = flow {
        emit(Resource.Loading())

        delay(500)

        when (val confirmedSelectedSession = repository.selectSession(selectedSession)) {
            is Resource.Success -> emit(Resource.Success(Unit))
            else -> emit(Resource.Failure(confirmedSelectedSession.error!!))
        }
    }
}