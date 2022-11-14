package com.sweak.diplomaexam.presentation.screens.lobby

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.use_case.lobby.GetLobbyState
import com.sweak.diplomaexam.domain.use_case.lobby.StartExamSession
import com.sweak.diplomaexam.presentation.screens.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val getLobbyState: GetLobbyState,
    private val startExamSession: StartExamSession
) : ViewModel() {

    var state by mutableStateOf(LobbyScreenState())

    private val sessionStartEventChannel = Channel<SessionStartEvent>()
    val sessionStartEvents = sessionStartEventChannel.receiveAsFlow()

    private var lastUnsuccessfulOperation: Runnable? = null

    init {
        fetchLobbyState()
    }

    private fun fetchLobbyState() {
        getLobbyState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.hasTheSessionBeenStarted) {
                            sessionStartEventChannel.send(SessionStartEvent)
                        } else {
                            state = state.copy(
                                user = it.data.currentUser,
                                hasOtherUserJoinedTheLobby = it.data.hasOtherUserJoinedTheLobby
                            )
                        }
                    }
                }
                is Resource.Loading -> state = state.copy(user = null)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        fetchLobbyState()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: LobbyScreenEvent) {
        when (event) {
            is LobbyScreenEvent.StartExam -> startSession()
            is LobbyScreenEvent.RetryAfterError -> {
                state = state.copy(errorMessage = null)

                lastUnsuccessfulOperation?.run()
                lastUnsuccessfulOperation = null
            }
        }
    }

    private fun startSession() {
        startExamSession().onEach {
            when (it) {
                is Resource.Success -> fetchLobbyState()
                is Resource.Loading -> state = state.copy(isSessionInStartingProcess = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        startSession()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getErrorMessage(error: Error?): UiText =
        when (error) {
            is Error.IOError -> UiText.StringResource(R.string.cant_reach_server)
            is Error.HttpError -> {
                if (error.message != null)
                    UiText.DynamicString(error.message)
                else
                    UiText.StringResource(R.string.unknown_error)
            }
            is Error.UnauthorizedError ->
                UiText.StringResource(R.string.no_permission)
            else -> UiText.StringResource(R.string.unknown_error)
        }

    object SessionStartEvent
}
