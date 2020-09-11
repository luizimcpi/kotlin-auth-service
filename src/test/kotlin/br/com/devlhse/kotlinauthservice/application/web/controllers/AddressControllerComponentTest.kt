package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.stubs.ViaCepStub
import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import io.restassured.http.ContentType.TEXT
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest


@ExtendWith(ComponentTestExtension::class)
class AddressControllerComponentTest(
    private val viaCepStub: ViaCepStub
) : KoinTest {

    private val baseUrl = "http://localhost:7000"

    private val defaultRequestSpecification: RequestSpecification = given()
        .baseUri(baseUrl)

    @Test
    fun `when controller request to viacep with valid cep string should return valid response`() {
        viaCepStub.returnSuccess()

        defaultRequestSpecification
            .`when`().get(VALID_ADDRESS_ROUTE)
            .then().statusCode(HttpStatus.SC_OK)
            .and().contentType(JSON)
            .body("cep", equalTo("11035-120"))
            .body("logradouro", equalTo("Rua Amaral Gurgel"))
            .body("complemento", equalTo(""))
            .body("bairro", equalTo("Ponta da Praia"))
            .body("localidade", equalTo("Santos"))
            .body("uf", equalTo("SP"))
            .body("ibge", equalTo("3548500"))
            .body("gia", equalTo("6336"))
            .body("ddd", equalTo("13"))
            .body("siafi", equalTo("7071"))
    }

    @Test
    fun `when controller request to viacep with invalid cep request string should throw exception`() {
        viaCepStub.returnBadRequest()

        defaultRequestSpecification
            .`when`().get(INVALID_ADDRESS_ROUTE)
            .then().statusCode(HttpStatus.SC_NO_CONTENT)
            .and().contentType(TEXT)

    }

    companion object {
        private const val VALID_ADDRESS_ROUTE = "/addresses/11035120"
        private const val INVALID_ADDRESS_ROUTE = "/addresses/abc-120"
    }
}