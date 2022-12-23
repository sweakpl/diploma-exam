package com.sweak.diplomaexam.domain.use_case.session_selection

import com.sweak.diplomaexam.data.repository.SessionSelectionRepositoryFake
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectExaminationSessionTest {

    private lateinit var selectExaminationSession: SelectExaminationSession
    private lateinit var sessionSelectionRepository: SessionSelectionRepositoryFake

    @Before
    fun setUp() {
        sessionSelectionRepository = SessionSelectionRepositoryFake()
        selectExaminationSession = SelectExaminationSession(sessionSelectionRepository)
    }

    @Test
    fun `If request is successful, then first value is Loading, second is Success`() = runTest {
        sessionSelectionRepository.isSuccessfulResponse = true

        val flowValues = selectExaminationSession.invoke(
            AvailableSession(43, "test.email@mail.com")
        ).toList()

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
        sessionSelectionRepository.isSuccessfulResponse = false

        val flowValues = selectExaminationSession.invoke(
            AvailableSession(43, "test.email@mail.com")
        ).toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        Assert.assertEquals(
            Resource.Failure::class.java,
            flowValues[1]::class.java
        )
    }

    @Test
    fun `If request is successful, then emits exactly two values`() = runTest {
        sessionSelectionRepository.isSuccessfulResponse = true

        Assert.assertEquals(
            2,
            selectExaminationSession.invoke(
                AvailableSession(43, "test.email@mail.com")
            ).count()
        )
    }

    @Test
    fun `If request is unsuccessful, then emits exactly two values`() = runTest {
        sessionSelectionRepository.isSuccessfulResponse = false

        Assert.assertEquals(
            2,
            selectExaminationSession.invoke(
                AvailableSession(43, "test.email@mail.com")
            ).count()
        )
    }
}