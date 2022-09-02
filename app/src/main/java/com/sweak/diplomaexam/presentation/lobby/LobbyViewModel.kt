package com.sweak.diplomaexam.presentation.lobby

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.use_case.lobby.GetCurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    getCurrentUser: GetCurrentUser
) : ViewModel() {

    var state by mutableStateOf(LobbyScreenState())

    init {
        getCurrentUser().onEach {
            when (it) {
                is Resource.Success -> {
                    state = state.copy(user = it.data)
                }
                else -> { /* no-op */ }
            }
        }.launchIn(viewModelScope)
    }
}
