package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.model.response.HealthCheckCompleteResponse
import br.com.devlhse.kotlinauthservice.domain.model.response.HealthCheckResponse
import br.com.devlhse.kotlinauthservice.domain.repositories.DatabaseHealthRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class HealthCheckServiceTest {

    private lateinit var repository: DatabaseHealthRepository

    @BeforeEach
    fun beforeEach(){
        repository = mockk(relaxed = true)
    }

    @AfterEach
    fun afterEach() {
        unmockkAll()
    }

    @Test
    fun `when execute health check status should return ok`() {

        val healthCheckService = HealthCheckService(repository)
        val expectedResponse = HealthCheckResponse(status = "OK")

        assertEquals(expectedResponse.status, healthCheckService.status().status)
    }

    @Test
    fun `when execute health check status complete should return ok`() {

        val healthCheckService = HealthCheckService(repository)
        val expectedResponse = HealthCheckCompleteResponse(status = "OK", databaseStatus = "OK")

        every { repository.status() } returns true

        assertEquals(expectedResponse.status, healthCheckService.statusComplete().status)
        assertEquals(expectedResponse.databaseStatus, healthCheckService.statusComplete().databaseStatus)
    }

    @Test
    fun `when execute health check status complete and database is down should return error`() {

        val healthCheckService = HealthCheckService(repository)
        val expectedResponse = HealthCheckCompleteResponse(status = "UNAVAILABLE", databaseStatus = "UNAVAILABLE")

        every { repository.status() } returns false

        assertEquals(expectedResponse.status, healthCheckService.statusComplete().status)
        assertEquals(expectedResponse.databaseStatus, healthCheckService.statusComplete().databaseStatus)
    }
}
