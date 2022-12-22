package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.domain.repository.SessionSelectionRepository

class SessionSelectionRepositoryFake : SessionSelectionRepository {

    var isSuccessfulResponse: Boolean = true

    override suspend fun getAvailableSessions(): Resource<List<AvailableSession>> {
        return if (isSuccessfulResponse) {
            Resource.Success(
                listOf(
                    AvailableSession(43, "test.email1@mail.com"),
                    AvailableSession(44, "test.email2@mail.com"),
                    AvailableSession(45, "test.email3@mail.com")
                )
            )
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }

    override suspend fun selectSession(selectedSession: AvailableSession): Resource<Unit> {
        return if (isSuccessfulResponse) {
            Resource.Success(Unit)
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }
}