package com.sweak.diplomaexam.presentation.screens.questions_draw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.use_case.questions_draw.*
import com.sweak.diplomaexam.presentation.screens.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class QuestionsDrawViewModel @Inject constructor(
    private val getQuestionsDrawState: GetQuestionsDrawState,
    private val drawNewQuestions: DrawNewQuestions,
    private val acceptDrawnQuestions: AcceptDrawnQuestions,
    private val requestQuestionsRedraw: RequestQuestionsRedraw,
    private val allowQuestionsRedraw: AllowQuestionsRedraw
) : ViewModel() {

    var state by mutableStateOf(QuestionsDrawScreenState())

    private val questionsConfirmedEventsChannel = Channel<QuestionsConfirmedEvent>()
    val questionsConfirmedEvents = questionsConfirmedEventsChannel.receiveAsFlow()

    private var lastUnsuccessfulOperation: Runnable? = null

    init {
        fetchQuestionsDrawState()
    }

    private fun fetchQuestionsDrawState() {
        getQuestionsDrawState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.areQuestionsConfirmed) {
                            questionsConfirmedEventsChannel.send(QuestionsConfirmedEvent)
                        } else {
                            state = state.copy(
                                currentUser = it.data.currentUser,
                                otherUser = it.data.otherUser,
                                questions = it.data.questions,
                                isLoadingResponse = false,
                                hasStudentRequestedRedraw = it.data.hasStudentRequestedRedraw,
                                waitingForDecisionFrom = it.data.waitingForDecisionFrom
                            )
                        }
                    }
                }
                is Resource.Loading -> {
                    state = if (state.currentUser == null) {
                        state.copy(currentUser = null)
                    } else {
                        state.copy(isLoadingResponse = true)
                    }
                }
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        fetchQuestionsDrawState()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: QuestionsDrawScreenEvent) {
        when (event) {
            is QuestionsDrawScreenEvent.DrawQuestions -> drawQuestions()
            is QuestionsDrawScreenEvent.RequestQuestionsRedraw -> requestRedraw()
            is QuestionsDrawScreenEvent.TryRequestQuestionsRedraw ->
                state = state.copy(redrawQuestionsDialogVisible = true)
            is QuestionsDrawScreenEvent.AcceptQuestions -> acceptQuestions()
            is QuestionsDrawScreenEvent.TryAcceptQuestions -> {
                if (!state.hasStudentRequestedRedraw) {
                    state = state.copy(acceptQuestionsDialogVisible = true)
                } else {
                    acceptQuestions()
                }
            }
            is QuestionsDrawScreenEvent.AllowRedraw -> allowRedraw()
            is QuestionsDrawScreenEvent.DisallowRedraw -> acceptQuestions()
            is QuestionsDrawScreenEvent.TryDisallowRedraw ->
                state = state.copy(disallowRedrawDialogVisible = true)
            is QuestionsDrawScreenEvent.HideAcceptQuestionsDialog ->
                state = state.copy(acceptQuestionsDialogVisible = false)
            is QuestionsDrawScreenEvent.HideDisallowRedrawDialog ->
                state = state.copy(disallowRedrawDialogVisible = false)
            is QuestionsDrawScreenEvent.HideRequestRedrawDialog ->
                state = state.copy(redrawQuestionsDialogVisible = false)
            is QuestionsDrawScreenEvent.RetryAfterError -> {
                state = state.copy(errorMessage = null)

                lastUnsuccessfulOperation?.run()
                lastUnsuccessfulOperation = null
            }
        }
    }

    private fun drawQuestions() {
        drawNewQuestions().onEach {
            when (it) {
                is Resource.Success -> fetchQuestionsDrawState()
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        drawQuestions()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun requestRedraw() {
        requestQuestionsRedraw().onEach {
            when (it) {
                is Resource.Success -> fetchQuestionsDrawState()
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        requestQuestionsRedraw()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun acceptQuestions() {
        acceptDrawnQuestions().onEach {
            when (it) {
                is Resource.Success -> fetchQuestionsDrawState()
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        acceptQuestions()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun allowRedraw() {
        allowQuestionsRedraw().onEach {
            when (it) {
                is Resource.Success -> fetchQuestionsDrawState()
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        allowRedraw()
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

    object QuestionsConfirmedEvent
}