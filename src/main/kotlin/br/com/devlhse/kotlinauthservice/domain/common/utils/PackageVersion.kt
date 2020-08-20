package br.com.devlhse.kotlinauthservice.domain.common.utils

import com.jcabi.manifests.Manifests
import org.apache.logging.log4j.LogManager

object PackageVersion {

    private val logger = LogManager.getLogger(PackageVersion::class.java.name)

    private val version by lazy { getManifestAttribute(PACKAGE_VERSION)}
    private val buildDate by lazy { getManifestAttribute(BUILD_DATE) }

    private fun getManifestAttribute(attribute: String): String? = try {
        Manifests.read(attribute)
    } catch (e: IllegalArgumentException) {
        logger.error("could not read the $attribute from manifest file message: ${e.message}")
        null
    }

    fun currentVersion() = version
    fun buildDate() = buildDate

    private const val PACKAGE_VERSION = "Package-Version"
    private const val BUILD_DATE = "Build-Date"
}
