package br.com.devlhse.kotlinauthservice.utils.stubs

import br.com.devlhse.kotlinauthservice.utils.TestUtils
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.eclipse.jetty.http.HttpStatus


class ViaCepStub(port: Int) : WireMockServer(port) {

    fun returnSuccess() {
        val responseBody = TestUtils.viaCepResponseSample("viacep_good_way.json")

        stubFor(
            WireMock.get(WireMock.urlPathMatching("/ws/11035120/json"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.OK_200)
                        .withBody(responseBody)
                )
        )
    }

    fun returnBadRequest() {
        val responseBody = TestUtils.viaCepResponseSample("viacep_bad_way.html")

        stubFor(
            WireMock.get(WireMock.urlPathMatching("/ws/abc-120/json"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST_400)
                        .withBody(responseBody)
                )
        )
    }
}