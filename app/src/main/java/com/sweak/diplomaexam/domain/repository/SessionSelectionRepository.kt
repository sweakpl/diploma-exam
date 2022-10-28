package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession

interface SessionSelectionRepository {

    suspend fun getAvailableSessions(): Resource<List<AvailableSession>>
    suspend fun selectSession(selectedSession: AvailableSession): Resource<Unit>
}