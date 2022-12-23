package com.sweak.diplomaexam.domain.use_case.lobby

import com.sweak.diplomaexam.data.repository.LobbyRepositoryFake
import com.sweak.diplomaexam.domain.model.common.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class StartExamSessionTest {

    private lateinit var startExamSession: StartExamSession
    private lateinit var lobbyRepository: LobbyRepositoryFake

    @Before
    fun setUp() {
        lobbyRepository = LobbyRepositoryFake()
        startExamSession = StartExamSession(lobbyRepository)
    }

    @Test
    fun `If request is successful, then first value is Loading, second is Success`() = runTest {
        lobbyRepository.isSuccessfulResponse = true

        val flowValues = startExamSession.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        Assert.assertEquals(
            Resource.Success::class.java,
            flowValues[1]::class.java
        )
    }

    @Test
    fun `If request is unsuccessful, then first value is Loading, second is Failure`() = runTest {
        lobbyRepository.isSuccessfulResponse = false

        val authenticationFlowValues = startExamSession.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            authenticationFlowValues.first()::class.java
        )

        Assert.assertEquals(
            Resource.Failure::class.java,
            authenticationFlowValues[1]::class.java
        )
    }

    @Test
    fun `If request is successful, then emits exactly two values`() = runTest {
        lobbyRepository.isSuccessfulResponse = true

        Assert.assertEquals(
            2,
            startExamSession.invoke().count()
        )
    }

    @Test
    fun `If request is unsuccessful, then emits exactly two values`() = runTest {
        lobbyRepository.isSuccessfulResponse = false

        Assert.assertEquals(
            2,
            startExamSession.invoke().count()
        )
    }
}