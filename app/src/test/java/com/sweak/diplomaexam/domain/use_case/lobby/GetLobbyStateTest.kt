package com.sweak.diplomaexam.domain.use_case.lobby

import com.sweak.diplomaexam.data.repository.LobbyRepositoryFake
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.UserRole
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLobbyStateTest {

    private lateinit var getLobbyState: GetLobbyState
    private lateinit var lobbyRepository: LobbyRepositoryFake

    @Before
    fun setUp() {
        lobbyRepository = LobbyRepositoryFake()
        getLobbyState = GetLobbyState(lobbyRepository)
    }

    @Test
    fun `If user is examiner and requests are successful, then first value is Loading, all the others are Success and flow terminates by itself eventually`() = runTest {
        lobbyRepository.isSuccessfulResponse = true
        lobbyRepository.userRole = UserRole.USER_EXAMINER

        val flowValues = getLobbyState.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        flowValues.subList(1, flowValues.size).forEach {
            Assert.assertEquals(
                Resource.Success::class.java,
                it::class.java
            )
        }
    }

    @Test
    fun `If user is student and requests are successful, then first value is Loading, all the others are Success and flow is not terminated by itself until at least 50 emissions`() = runTest {
        lobbyRepository.isSuccessfulResponse = true
        lobbyRepository.userRole = UserRole.USER_STUDENT

        val flowValues = getLobbyState.invoke().take(50).toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        flowValues.subList(1, flowValues.size).forEach {
            Assert.assertEquals(
                Resource.Success::class.java,
                it::class.java
            )
        }
    }

    @Test
    fun `If some request is unsuccessful, then first value is Loading, and last one is Failure`() = runTest {
        lobbyRepository.isSuccessfulResponse = false
        lobbyRepository.userRole = UserRole.USER_EXAMINER

        val flowValues = getLobbyState.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        Assert.assertEquals(
            Resource.Failure::class.java,
            flowValues.last()::class.java
        )
    }
}