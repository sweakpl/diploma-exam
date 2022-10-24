package com.sweak.diplomaexam.domain.model.session_selection

data class SessionSelectionState(
    val availableSessions: List<AvailableSession>? = null,
    val isSessionSelectionConfirmed: Boolean = false
)
