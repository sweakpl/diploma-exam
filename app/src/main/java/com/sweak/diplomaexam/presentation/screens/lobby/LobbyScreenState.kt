package com.sweak.diplomaexam.presentation.screens.lobby

import com.sweak.diplomaexam.domain.model.common.User

data class LobbyScreenState(
    val user: User? = null,
    val hasOtherUserJoinedTheLobby: Boolean = false,
    val isSessionInStartingProcess: Boolean = false
)