package com.sweak.diplomaexam.presentation.questions_draw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.use_case.questions_draw.GetQuestionsDrawState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QuestionsDrawViewModel @Inject constructor(
    getQuestionsDrawState: GetQuestionsDrawState
) : ViewModel() {

    var state by mutableStateOf(QuestionsDrawState())

    init {
        getQuestionsDrawState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        state = state.copy(
                            currentUser = it.data.currentUser,
                            otherUser = it.data.otherUser
                        )
                    }
                }
                else -> { /* no-op */ }
            }
        }.launchIn(viewModelScope)
    }
}