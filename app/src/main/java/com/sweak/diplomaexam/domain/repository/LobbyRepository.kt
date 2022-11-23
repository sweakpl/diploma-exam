package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.lobby.LobbyState

interface LobbyRepository {

    suspend fun getLobbyState(): Resource<LobbyState>
    suspend fun startExaminationSession(): Resource<Unit>
}