package com.sweak.diplomaexam.presentation.screens.lobby

import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.presentation.screens.common.UiText

data class LobbyScreenState(
    val user: User? = null,
    val hasOtherUserJoinedTheLobby: Boolean = false,
    val isSessionInStartingProcess: Boolean = false,
    val errorMessage: UiText? = null
)
