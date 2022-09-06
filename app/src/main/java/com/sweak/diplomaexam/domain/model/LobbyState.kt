package com.sweak.diplomaexam.domain.model

data class LobbyState(
    val currentUser: User,
    val hasOtherUserJoinedTheLobby: Boolean,
    val hasTheSessionBeenStarted: Boolean
)
