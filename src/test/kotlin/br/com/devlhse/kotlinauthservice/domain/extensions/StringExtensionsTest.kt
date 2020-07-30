package br.com.devlhse.kotlinauthservice.domain.extensions

import org.junit.Assert.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StringExtensionsTest {

    @Test
    fun `when verify valid email should return true`() {
        val validEmail = "luiz@gmail.com"
        assertTrue(validEmail.isEmailValid())
    }

    @Test
    fun `when verify invalid email should return false`() {
        val invalidEmail = "luiz@gmail"
        assertFalse(invalidEmail.isEmailValid())
    }
}
