package com.sweak.diplomaexam.domain.use_case.session_selection

import com.sweak.diplomaexam.domain.DUMMY_IS_SESSION_SELECTION_CONFIRMED
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.session_selection.SessionSelectionState
import com.sweak.diplomaexam.domain.repository.SessionSelectionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSessionSelectionState @Inject constructor(
    private val repository: SessionSelectionRepository
) {
    private var state: SessionSelectionState = SessionSelectionState()

    operator fun invoke() = flow {
        emit(Resource.Loading())

        when (val availableSessions = repository.getAvailableSessions()) {
            is Resource.Success -> {
                state = state.copy(availableSessions = availableSessions.data)
                emit(Resource.Success(state))
            }
            is Resource.Failure -> emit(Resource.Failure(availableSessions.error!!))
            is Resource.Loading -> { /* no-op */ }
        }

        while (true) {
            delay(3000)

            if (DUMMY_IS_SESSION_SELECTION_CONFIRMED) {
                state = state.copy(isSessionSelectionConfirmed = true)
                emit(Resource.Success(state))

                break
            }
        }
    }
}