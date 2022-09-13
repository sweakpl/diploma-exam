package com.sweak.diplomaexam.presentation.questions_draw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.use_case.questions_draw.AllowQuestionsRedraw
import com.sweak.diplomaexam.domain.use_case.questions_draw.AcceptDrawnQuestions
import com.sweak.diplomaexam.domain.use_case.questions_draw.DrawNewQuestions
import com.sweak.diplomaexam.domain.use_case.questions_draw.GetQuestionsDrawState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsDrawViewModel @Inject constructor(
    getQuestionsDrawState: GetQuestionsDrawState,
    private val drawNewQuestions: DrawNewQuestions,
    private val acceptDrawnQuestions: AcceptDrawnQuestions,
    private val allowQuestionsRedraw: AllowQuestionsRedraw
) : ViewModel() {

    var state by mutableStateOf(QuestionsDrawScreenState())

    private val questionsConfirmedEventsChannel = Channel<QuestionsConfirmedEvent>()
    val questionsConfirmedEvents = questionsConfirmedEventsChannel.receiveAsFlow()

    private var hasFinalizedRequest: Boolean = true

    init {
        getQuestionsDrawState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.areQuestionsConfirmed) {
                            questionsConfirmedEventsChannel.send(QuestionsConfirmedEvent.Success)
                        } else {
                            state = state.copy(
                                currentUser = it.data.currentUser,
                                otherUser = it.data.otherUser,
                                questions = it.data.questions,
                                isLoadingResponse = !hasFinalizedRequest,
                                hasStudentRequestedRedraw = it.data.hasStudentRequestedRedraw,
                                waitingForDecisionFrom = it.data.waitingForDecisionFrom
                            )
                        }
                    }
                }
                else -> { /* no-op */ }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: QuestionsDrawScreenEvent) {
        when (event) {
            is QuestionsDrawScreenEvent.DrawQuestions -> drawQuestions()
            is QuestionsDrawScreenEvent.AcceptQuestions -> acceptQuestions()
            is QuestionsDrawScreenEvent.AllowRedraw -> allowRedraw()
        }
    }

    private fun drawQuestions() =
        performRequest { viewModelScope.launch { drawNewQuestions() } }
    private fun acceptQuestions() =
        performRequest { viewModelScope.launch { acceptDrawnQuestions() } }
    private fun allowRedraw() =
        performRequest { viewModelScope.launch { allowQuestionsRedraw() } }

    private fun performRequest(request: () -> Unit) {
        hasFinalizedRequest = false
        state = state.copy(isLoadingResponse = true)
        request()
        hasFinalizedRequest = true
    }

    sealed class QuestionsConfirmedEvent {
        object Success : QuestionsConfirmedEvent()
    }
}