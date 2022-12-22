package com.sweak.diplomaexam.domain.use_case.session_selection

import com.sweak.diplomaexam.data.repository.SessionSelectionRepositoryFake
import com.sweak.diplomaexam.domain.model.common.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAvailableSessionsTest {

    private lateinit var getAvailableSessions: GetAvailableSessions
    private lateinit var sessionSelectionRepository: SessionSelectionRepositoryFake

    @Before
    fun setUp() {
        sessionSelectionRepository = SessionSelectionRepositoryFake()
        getAvailableSessions = GetAvailableSessions(sessionSelectionRepository)
    }

    @Test
    fun `Request is successful, first value is Loading, second is Success`() = runTest {
        sessionSelectionRepository.isSuccessfulResponse = true

        val flowValues = getAvailableSessions.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues[0]::class.java
        )

        Assert.assertEquals(
            Resource.Success::class.java,
            flowValues[1]::class.java
        )
    }

    @Test
    fun `Request is unsuccessful, first value is Loading, second is Failure`() = runTest {
        sessionSelectionRepository.isSuccessfulResponse = false

        val authenticationFlowValues = getAvailableSessions.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            authenticationFlowValues[0]::class.java
        )

        Assert.assertEquals(
            Resource.Failure::class.java,
            authenticationFlowValues[1]::class.java
        )
    }

    @Test
    fun `Request is successful, emits exactly two values`() = runTest {
        sessionSelectionRepository.isSuccessfulResponse = true

        Assert.assertEquals(
            2,
            getAvailableSessions.invoke().count()
        )
    }

    @Test
    fun `Request is unsuccessful, emits exactly two values`() = runTest {
        sessionSelectionRepository.isSuccessfulResponse = false

        Assert.assertEquals(
            2,
            getAvailableSessions.invoke().count()
        )
    }
}