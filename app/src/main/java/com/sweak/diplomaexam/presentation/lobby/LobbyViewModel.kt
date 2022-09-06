package com.sweak.diplomaexam.presentation.lobby

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.use_case.lobby.GetLobbyState
import com.sweak.diplomaexam.domain.use_case.lobby.StartExamSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    getLobbyState: GetLobbyState,
    private val startExamSession: StartExamSession
) : ViewModel() {

    var state by mutableStateOf(LobbyScreenState())

    private val sessionStartEventChannel = Channel<SessionStartEvent>()
    val sessionStartEvents = sessionStartEventChannel.receiveAsFlow()

    init {
        getLobbyState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.hasTheSessionBeenStarted) {
                            sessionStartEventChannel.send(SessionStartEvent.Success)
                        } else {
                            state = state.copy(
                                user = it.data.currentUser,
                                hasOtherUserJoinedTheLobby = it.data.hasOtherUserJoinedTheLobby
                            )
                        }
                    }
                }
                else -> { /* no-op */ }
            }
        }.launchIn(viewModelScope)
    }

    fun startSession() = viewModelScope.launch { startExamSession() }

    sealed class SessionStartEvent {
        object Success : SessionStartEvent()
    }
}
