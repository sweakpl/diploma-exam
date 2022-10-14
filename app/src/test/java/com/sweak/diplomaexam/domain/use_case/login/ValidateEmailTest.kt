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
    fun `Email is valid, returns true`() {
        val result = validateEmail("anna.marchewka@pk.edu.pl")

        assertEquals(result, true)
    }

    @Test
    fun `Student email is valid, returns true`() {
        val result = validateEmail("janek.kowalski@student.pk.edu.pl")

        assertEquals(result, true)
    }

    @Test
    fun `Two-part name email is valid, returns true`() {
        val result = validateEmail("barbara.jankowicz-grzyb@pk.edu.pl")

        assertEquals(result, true)
    }

    @Test
    fun `Two-part name student email is valid, returns true`() {
        val result = validateEmail("stanislaw.michalowski-kot@student.pk.edu.pl")

        assertEquals(result, true)
    }

    @Test
    fun `No-dot name email is valid, returns true`() {
        val result = validateEmail("tniedzwiedzki@pk.edu.pl")

        assertEquals(result, true)
    }

    @Test
    fun `No-dot name student email is valid, returns true`() {
        val result = validateEmail("anowak@student.pk.edu.pl")

        assertEquals(result, true)
    }

    @Test
    fun `Email with polish character, returns false`() {
        val result = validateEmail("adaś.nowak@student.pk.edu.pl")

        assertEquals(result, false)
    }

    @Test
    fun `Email with wrong first-level domain, returns false`() {
        val result = validateEmail("jan.koral@pw.edu.pl")

        assertEquals(result, false)
    }

    @Test
    fun `Email with wrong third-level domain, returns false`() {
        val result = validateEmail("marian.grzyb@pk.edu.pol")

        assertEquals(result, false)
    }

    @Test
    fun `Email without the @ character, returns false`() {
        val result = validateEmail("janusz.szary.student.pk.edu.pl")

        assertEquals(result, false)
    }

    @Test
    fun `Email contains numbers, returns false`() {
        val result = validateEmail("waclaw.grabczyk123@pk.edu.pl")

        assertEquals(result, false)
    }

    @Test
    fun `Email is blank, returns false`() {
        val result = validateEmail("")

        assertEquals(result, false)
    }
}