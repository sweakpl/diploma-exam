package com.sweak.diplomaexam.presentation.screens.session_selection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.domain.use_case.session_selection.GetSessionSelectionState
import com.sweak.diplomaexam.domain.use_case.session_selection.SelectExaminationSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionSelectionViewModel @Inject constructor(
    getSessionSelectionState: GetSessionSelectionState,
    private val selectExaminationSession: SelectExaminationSession
) : ViewModel() {

    var state by mutableStateOf(SessionSelectionScreenState())

    private val sessionConfirmedEventsChannel = Channel<SessionConfirmedEvent>()
    val sessionConfirmedEvents = sessionConfirmedEventsChannel.receiveAsFlow()

    private var hasFinalizedRequest: Boolean = true

    init {
        getSessionSelectionState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.isSessionSelectionConfirmed) {
                            sessionConfirmedEventsChannel.send(SessionConfirmedEvent.Success)
                        } else {
                            state = state.copy(
                                isLoadingResponse = !hasFinalizedRequest,
                                availableSessions = it.data.availableSessions
                            )
                        }
                    }
                }
                else -> { /* no-op */ }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: SessionSelectionScreenEvent) {
        when (event) {
            is SessionSelectionScreenEvent.SelectAvailableSession ->
                state = state.copy(
                    selectedSession = event.availableSession,
                    sessionSelectionConfirmationDialogVisible = true
                )
            is SessionSelectionScreenEvent.HideSessionSelectionConfirmationDialog ->
                state = state.copy(
                    selectedSession =
                    if (event.isConfirmingAfterHiding) state.selectedSession else null,
                    sessionSelectionConfirmationDialogVisible = false
                )
            is SessionSelectionScreenEvent.ConfirmSessionSelection -> {
                state.selectedSession?.let {
                    confirmSessionSelection(it)
                }
            }
        }
    }

    private fun confirmSessionSelection(availableSession: AvailableSession) = performRequest {
        viewModelScope.launch { selectExaminationSession(availableSession) }
    }

    private fun performRequest(request: () -> Unit) {
        hasFinalizedRequest = false
        state = state.copy(isLoadingResponse = true)
        request()
        hasFinalizedRequest = true
    }

    sealed class SessionConfirmedEvent {
        object Success : SessionConfirmedEvent()
    }
}