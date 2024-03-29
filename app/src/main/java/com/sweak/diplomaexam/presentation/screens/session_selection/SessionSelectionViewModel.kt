package com.sweak.diplomaexam.presentation.screens.session_selection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.domain.use_case.session_selection.GetAvailableSessions
import com.sweak.diplomaexam.domain.use_case.session_selection.SelectExaminationSession
import com.sweak.diplomaexam.presentation.screens.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SessionSelectionViewModel @Inject constructor(
    private val getAvailableSessions: GetAvailableSessions,
    private val selectExaminationSession: SelectExaminationSession
) : ViewModel() {

    var state by mutableStateOf(SessionSelectionScreenState())

    private val sessionConfirmedEventsChannel = Channel<SessionConfirmedEvent>()
    val sessionConfirmedEvents = sessionConfirmedEventsChannel.receiveAsFlow()

    private var lastUnsuccessfulOperation: Runnable? = null

    init {
        fetchAvailableSessions()
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
            is SessionSelectionScreenEvent.RetryAfterError -> {
                state = state.copy(errorMessage = null)

                lastUnsuccessfulOperation?.run()
                lastUnsuccessfulOperation = null
            }
        }
    }

    private fun fetchAvailableSessions() {
        getAvailableSessions().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        state = state.copy(
                            isLoadingResponse = false,
                            availableSessions = it.data
                        )
                    }
                }
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        fetchAvailableSessions()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun confirmSessionSelection(selectedSession: AvailableSession) {
        selectExaminationSession(selectedSession).onEach {
            when (it) {
                is Resource.Success -> {
                    sessionConfirmedEventsChannel.send(SessionConfirmedEvent)
                }
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        confirmSessionSelection(selectedSession)
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
            is Error.InternalServerError ->
                UiText.StringResource(R.string.internal_server_error_occurred)
            else -> UiText.StringResource(R.string.unknown_error)
        }

    object SessionConfirmedEvent
}