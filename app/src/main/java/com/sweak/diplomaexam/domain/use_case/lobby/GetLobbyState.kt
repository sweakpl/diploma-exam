package com.sweak.diplomaexam.domain.use_case.lobby

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.repository.LobbyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLobbyState @Inject constructor(
    private val repository: LobbyRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading())

        while (true) {
            delay(500)

            when (val lobbyState = repository.getLobbyState()) {
                is Resource.Success -> {
                    emit(Resource.Success(lobbyState.data))

                    if (lobbyState.data?.hasOtherUserJoinedTheLobby == true &&
                            lobbyState.data.currentUser.role == UserRole.USER_EXAMINER
                    ) {
                        break
                    }
                }
                else -> {
                    emit(Resource.Failure(lobbyState.error!!))
                    break
                }
            }

            delay(2500)
        }
    }
}