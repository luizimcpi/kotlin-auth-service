package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.common.utils.PackageVersion
import br.com.devlhse.kotlinauthservice.domain.model.response.HealthCheckCompleteResponse
import br.com.devlhse.kotlinauthservice.domain.model.response.HealthCheckResponse
import br.com.devlhse.kotlinauthservice.domain.repositories.DatabaseHealthRepository
import org.apache.logging.log4j.LogManager

class HealthCheckService(private val healthRepository: DatabaseHealthRepository) {

    private val logger = LogManager.getLogger(HealthCheckService::class.java.name)

    fun status(): HealthCheckResponse {
        logger.info("Checking application health...")
        return HealthCheckResponse(status = STATUS_OK)
    }

    fun statusComplete(): HealthCheckCompleteResponse {
        logger.info("Checking complete application health...")
        val healthRepositoryStatus = healthRepository.status()
        val criticalStatus = listOf(healthRepositoryStatus)
        val status = finalStatus(criticalStatus)
        return HealthCheckCompleteResponse(
                packageVersion = PackageVersion.currentVersion(),
                buildDate = PackageVersion.buildDate(),
                status = status,
                databaseStatus = if(healthRepositoryStatus) STATUS_OK else STATUS_UNAVAILABLE
        )
    }

    private fun finalStatus(criticalStatus: List<Boolean>) =
        if (criticalStatus.stream().anyMatch { status -> !status }) STATUS_UNAVAILABLE else STATUS_OK

    companion object {
        private const val STATUS_OK = "OK"
        private const val STATUS_UNAVAILABLE = "UNAVAILABLE"
    }
}
