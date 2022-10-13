package com.sweak.diplomaexam.domain.use_case.lobby

import com.sweak.diplomaexam.domain.DUMMY_HAS_SESSION_BEEN_STARTED
import com.sweak.diplomaexam.domain.DUMMY_USER_EMAIL
import com.sweak.diplomaexam.domain.DUMMY_USER_ROLE
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.lobby.LobbyState
import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.domain.model.common.UserRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLobbyState @Inject constructor() {

    // Emitting dummy data to test app behavior
    operator fun invoke() = flow<Resource<LobbyState>> {
        delay(3000)
        emit(
            Resource.Success(
                LobbyState(
                    currentUser = User(DUMMY_USER_ROLE, DUMMY_USER_EMAIL),
                    hasOtherUserJoinedTheLobby = false,
                    hasTheSessionBeenStarted = false
                )
            )
        )
        delay(5000)
        emit(
            Resource.Success(
                LobbyState(
                    currentUser = User(DUMMY_USER_ROLE, DUMMY_USER_EMAIL),
                    hasOtherUserJoinedTheLobby = true,
                    hasTheSessionBeenStarted = false
                )
            )
        )
        while (true) {
            delay(2000)
            emit(
                Resource.Success(
                    LobbyState(
                        currentUser = User(DUMMY_USER_ROLE, DUMMY_USER_EMAIL),
                        hasOtherUserJoinedTheLobby = true,
                        hasTheSessionBeenStarted =
                        if (DUMMY_USER_ROLE == UserRole.USER_STUDENT) {
                            true
                        } else {
                            DUMMY_HAS_SESSION_BEEN_STARTED
                        }
                    )
                )
            )
        }
    }
}