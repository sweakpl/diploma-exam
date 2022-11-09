package com.sweak.diplomaexam.presentation.screens.lobby

sealed class LobbyScreenEvent {
    object StartExam : LobbyScreenEvent()
    object RetryAfterError : LobbyScreenEvent()
}
