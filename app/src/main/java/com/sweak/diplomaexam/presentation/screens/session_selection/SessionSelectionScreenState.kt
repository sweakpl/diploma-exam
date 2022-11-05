package com.sweak.diplomaexam.presentation.screens.session_selection

import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.presentation.screens.common.UiText

data class SessionSelectionScreenState(
    val isLoadingResponse: Boolean = true,
    val availableSessions: List<AvailableSession>? = null,
    val selectedSession: AvailableSession? = null,
    val sessionSelectionConfirmationDialogVisible: Boolean = false,
    val errorMessage: UiText? = null
)
