package com.sweak.diplomaexam.presentation.lobby

import com.sweak.diplomaexam.domain.model.User

data class LobbyScreenState(
    val user: User? = null,
    val hasOtherUserJoined: Boolean = false
)
