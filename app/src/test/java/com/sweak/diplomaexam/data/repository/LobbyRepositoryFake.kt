package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.model.lobby.LobbyState
import com.sweak.diplomaexam.domain.repository.LobbyRepository

class LobbyRepositoryFake : LobbyRepository {

    var isSuccessfulResponse: Boolean = true
    var userRole: UserRole = UserRole.USER_EXAMINER
    private var otherUserJoinedCounter: Int = 0
    private var failureCounter: Int = 0

    override suspend fun getLobbyState(): Resource<LobbyState> {
        return if (failureCounter++ != 3 || isSuccessfulResponse) {
            Resource.Success(
                LobbyState(
                    User(userRole, "test.email@mail.com"),
                    hasOtherUserJoinedTheLobby = otherUserJoinedCounter++ == 3,
                    hasTheSessionBeenStarted = false
                )
            )
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override suspend fun startExaminationSession(): Resource<Unit> {
        return if (isSuccessfulResponse) {
            Resource.Success(Unit)
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }
}