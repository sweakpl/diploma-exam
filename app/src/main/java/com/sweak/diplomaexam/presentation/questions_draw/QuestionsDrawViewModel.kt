package com.sweak.diplomaexam.presentation.questions_draw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.use_case.questions_draw.DrawQuestions
import com.sweak.diplomaexam.domain.use_case.questions_draw.GetQuestionsDrawState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsDrawViewModel @Inject constructor(
    getQuestionsDrawState: GetQuestionsDrawState,
    private val drawQuestions: DrawQuestions
) : ViewModel() {
    var state by mutableStateOf(QuestionsDrawScreenState())

    init {
        getQuestionsDrawState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        state = state.copy(
                            currentUser = it.data.currentUser,
                            otherUser = it.data.otherUser,
                            questions = it.data.questions
                        )
                    }
                }
                else -> { /* no-op */ }
            }
        }.launchIn(viewModelScope)
    }

    fun drawNewQuestions() = viewModelScope.launch {
        state = state.copy(areQuestionsInDrawingProcess = true)
        drawQuestions()
    }
}