package com.sweak.diplomaexam.presentation.questions_answering

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.use_case.questions_answering.ConfirmReadinessToAnswer
import com.sweak.diplomaexam.domain.use_case.questions_answering.GetQuestionsAnsweringState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsAnsweringViewModel @Inject constructor(
    getQuestionsAnsweringState: GetQuestionsAnsweringState,
    private val confirmReadinessToAnswer: ConfirmReadinessToAnswer,
) : ViewModel() {

    var state by mutableStateOf(QuestionsAnsweringScreenState())

    private var hasFinalizedRequest: Boolean = true

    init {
        getQuestionsAnsweringState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        state = state.copy(
                            currentUser = it.data.currentUser,
                            otherUser = it.data.otherUser,
                            questions = it.data.questions,
                            isLoadingResponse = !hasFinalizedRequest,
                            isWaitingForStudentReadiness = it.data.isWaitingForStudentReadiness
                        )
                    }
                }
                else -> { /* no-op */ }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: QuestionsAnsweringScreenEvent) {
        when (event) {
            is QuestionsAnsweringScreenEvent.ConfirmReadinessToAnswer -> confirmReadiness()
        }
    }

    private fun confirmReadiness() =
        performRequest { viewModelScope.launch { confirmReadinessToAnswer() } }

    private fun performRequest(request: () -> Unit) {
        hasFinalizedRequest = false
        state = state.copy(isLoadingResponse = true)
        request()
        hasFinalizedRequest = true
    }
}