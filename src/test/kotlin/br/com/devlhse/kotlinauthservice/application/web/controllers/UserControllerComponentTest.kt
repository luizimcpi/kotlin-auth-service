package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.getResourceContent
import io.restassured.RestAssured
import io.restassured.http.ContentType.JSON
import io.restassured.http.ContentType.TEXT
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest

@ExtendWith(ComponentTestExtension::class)
class UserControllerComponentTest: KoinTest {

    private val baseUrl = "http://localhost:7000"
    private val validBody = "/requests/valid_body_user_request.json".getResourceContent()
    private val validRequestContentType = "application/json"
    private val defaultRequestSpecification: RequestSpecification = RestAssured.given()
        .baseUri(baseUrl)
        .contentType(validRequestContentType)
        .body(validBody)

    @Test
    fun `when user controller request to include new user should return valid success`() {

        defaultRequestSpecification
            .`when`().post(VALID_USERS_ROUTE)
            .then().statusCode(HttpStatus.SC_CREATED)
            .and().contentType(TEXT)
    }

    @Test
    fun `when user controller request to include an existent user should return conflict`() {

        defaultRequestSpecification
            .`when`().post(VALID_USERS_ROUTE)
            .then().statusCode(HttpStatus.SC_CREATED)
            .and().contentType(TEXT)

        defaultRequestSpecification
            .`when`().post(VALID_USERS_ROUTE)
            .then().statusCode(HttpStatus.SC_CONFLICT)
            .and().contentType(JSON)
    }

    companion object {
        private const val VALID_USERS_ROUTE = "/users"
    }
}