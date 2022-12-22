package com.sweak.diplomaexam.domain.use_case.login

import com.sweak.diplomaexam.data.repository.AuthenticationRepositoryFake
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.UserRole
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthenticateUserTest {

    private lateinit var authenticateUser: AuthenticateUser
    private lateinit var authenticationRepository: AuthenticationRepositoryFake

    @Before
    fun setUp() {
        authenticationRepository = AuthenticationRepositoryFake()
        authenticateUser = AuthenticateUser(authenticationRepository)
    }

    @Test
    fun `Authentication is successful, first value is Loading, second is Success`() = runTest {
        authenticationRepository.isSuccessfulResponse = true

        val authenticationFlowValues = authenticateUser.invoke(
            "test.email@mail.com",
            "test-password-123",
            UserRole.USER_EXAMINER
        ).toList()

        assertEquals(
            Resource.Loading::class.java,
            authenticationFlowValues[0]::class.java
        )

        assertEquals(
            Resource.Success::class.java,
            authenticationFlowValues[1]::class.java
        )
    }

    @Test
    fun `Authentication is unsuccessful, first value is Loading, second is Failure`() = runTest {
        authenticationRepository.isSuccessfulResponse = false

        val authenticationFlowValues = authenticateUser.invoke(
            "test.email@mail.com",
            "test-password-123",
            UserRole.USER_EXAMINER
        ).toList()

        assertEquals(
            Resource.Loading::class.java,
            authenticationFlowValues[0]::class.java
        )

        assertEquals(
            Resource.Failure::class.java,
            authenticationFlowValues[1]::class.java
        )
    }

    @Test
    fun `Authentication is successful, emits exactly two values`() = runTest {
        authenticationRepository.isSuccessfulResponse = true

        assertEquals(
            2,
            authenticateUser.invoke(
                "test.email@mail.com",
                "test-password-123",
                UserRole.USER_EXAMINER
            ).count()
        )
    }

    @Test
    fun `Authentication is unsuccessful, emits exactly two values`() = runTest {
        authenticationRepository.isSuccessfulResponse = false

        assertEquals(
            2,
            authenticateUser.invoke(
                "test.email@mail.com",
                "test-password-123",
                UserRole.USER_EXAMINER
            ).count()
        )
    }
}