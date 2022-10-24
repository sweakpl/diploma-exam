package com.sweak.diplomaexam.domain.use_case.session_selection

import com.sweak.diplomaexam.domain.DUMMY_IS_SESSION_SELECTION_CONFIRMED
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.domain.model.session_selection.SessionSelectionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSessionSelectionState @Inject constructor() {

    private var state: SessionSelectionState = SessionSelectionState()

    operator fun invoke() = flow<Resource<SessionSelectionState>> {
        delay(3000)

        state = state.copy(
            availableSessions = listOf(
                AvailableSession(0, "janek.kowalski@student.pk.edu.pl"),
                AvailableSession(1, "stanislaw.michalowski-kot@student.pk.edu.pl"),
                AvailableSession(2, "adam.kowalski@student.pk.edu.pl"),
                AvailableSession(3, "arkadiusz.ziemny@student.pk.edu.pl")
            )
        )

        emit(Resource.Success(state))

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