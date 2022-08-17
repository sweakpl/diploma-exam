package com.sweak.diplomaexam.domain.use_case.login

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidateEmailTest {

    private lateinit var validateEmail: ValidateEmail

    @Before
    fun setUp() {
        validateEmail = ValidateEmail()
    }

    @Test
    fun `Email is valid, successfully validates`() {
        val result = validateEmail("anna.marchewka@pk.edu.pl")

        assertEquals(result.successful, true)
    }

    @Test
    fun `Student email is valid, successfully validates`() {
        val result = validateEmail("janek.kowalski@student.pk.edu.pl")

        assertEquals(result.successful, true)
    }

    @Test
    fun `Two-part name email is valid, successfully validates`() {
        val result = validateEmail("barbara.jankowicz-grzyb@pk.edu.pl")

        assertEquals(result.successful, true)
    }

    @Test
    fun `Two-part name student email is valid, successfully validates`() {
        val result = validateEmail("stanislaw.michalowski-kot@student.pk.edu.pl")

        assertEquals(result.successful, true)
    }

    @Test
    fun `Email with polish character, returns error`() {
        val result = validateEmail("adaś.nowak@student.pk.edu.pl")

        assertEquals(result.successful, false)
    }

    @Test
    fun `Email with wrong first-level domain, returns error`() {
        val result = validateEmail("jan.koral@pw.edu.pl")

        assertEquals(result.successful, false)
    }

    @Test
    fun `Email with wrong third-level domain, returns error`() {
        val result = validateEmail("marian.grzyb@pk.edu.pol")

        assertEquals(result.successful, false)
    }

    @Test
    fun `Email without the @ character, returns error`() {
        val result = validateEmail("janusz.szary.student.pk.edu.pl")

        assertEquals(result.successful, false)
    }

    @Test
    fun `Email is blank, returns error`() {
        val result = validateEmail("")

        assertEquals(result.successful, false)
    }
}