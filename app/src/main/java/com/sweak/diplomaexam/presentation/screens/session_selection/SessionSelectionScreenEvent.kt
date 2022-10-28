package com.sweak.diplomaexam.presentation.screens.session_selection

import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession

sealed class SessionSelectionScreenEvent {
    data class SelectAvailableSession(val availableSession: AvailableSession) :
        SessionSelectionScreenEvent()
    data class HideSessionSelectionConfirmationDialog(val isConfirmingAfterHiding: Boolean) :
        SessionSelectionScreenEvent()
    object ConfirmSessionSelection : SessionSelectionScreenEvent()
    object RetryAfterError : SessionSelectionScreenEvent()
}
