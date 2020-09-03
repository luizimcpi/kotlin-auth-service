package br.com.devlhse.kotlinauthservice.utils

import br.com.devlhse.kotlinauthservice.utils.stubs.ViaCepStub
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class ComponentTestExtension :
    BeforeAllCallback, BeforeEachCallback, ParameterResolver, ExtensionContext.Store.CloseableResource {

    companion object {
        var applicationAlreadyStarted = false
    }

    override fun beforeAll(context: ExtensionContext?) {
        if (!applicationAlreadyStarted) {
            applicationAlreadyStarted = true
            ComponentTestEnvironmentSetting.start()
        }
    }

    override fun beforeEach(context: ExtensionContext?) {
        ComponentTestEnvironmentSetting.prepareTest()
    }

    override fun close() {
        ComponentTestEnvironmentSetting.tearDown(true)
    }

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?) =
        parameterContext.parameter.type.let { it == ViaCepStub::class.java }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?) =
        when (parameterContext.parameter.type) {
            ViaCepStub::class.java -> ComponentTestEnvironmentSetting.viaCepStub
            else -> throw IllegalArgumentException("Unsupported parameter [${parameterContext.parameter.type}]")
        }
}