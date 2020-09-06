package br.com.devlhse.kotlinauthservice.utils

import br.com.devlhse.kotlinauthservice.application.web.AuthServiceEntryPoint
import br.com.devlhse.kotlinauthservice.utils.stubs.ViaCepStub
import io.mockk.unmockkAll

object ComponentTestEnvironmentSetting {

    val viaCepStub = ViaCepStub(9097)

    fun start() {
        initDatabase()
        initExternalServices()
        AuthServiceEntryPoint.init()
    }

    fun tearDown(unmockAll: Boolean) {
        AuthServiceEntryPoint.stop()
        stopExternalServices()
        closeDatabase()

        if (unmockAll) unmockkAll()
    }

    fun prepareTest() {
        PostgresMock.resetDb()
    }

    fun initDatabase() {
        PostgresMock.init()
    }

    fun closeDatabase() {
        PostgresMock.close()
    }

    fun initExternalServices() {
        viaCepStub.start()
    }

    fun stopExternalServices() {
        viaCepStub.stop()
    }
}