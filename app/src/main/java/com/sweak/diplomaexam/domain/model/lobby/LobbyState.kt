package com.sweak.diplomaexam.domain.model.lobby

import com.sweak.diplomaexam.domain.model.common.User

data class LobbyState(
    val currentUser: User,
    val hasOtherUserJoinedTheLobby: Boolean,
    val hasTheSessionBeenStarted: Boolean
)
