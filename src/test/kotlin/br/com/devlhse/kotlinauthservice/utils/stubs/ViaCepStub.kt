package br.com.devlhse.kotlinauthservice.utils.stubs

import br.com.devlhse.kotlinauthservice.utils.TestUtils
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.eclipse.jetty.http.HttpStatus


class ViaCepStub(port: Int) : WireMockServer(port) {

    fun returnSuccess() {
        val responseBody = TestUtils.viaCepResponseSample("viacep_good_way")

        stubFor(
            WireMock.post(WireMock.urlPathMatching("/11035120/json"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.OK_200)
                        .withBody(responseBody)
                )
        )
    }
}