package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.config.ObjectMapperConfig
import br.com.devlhse.kotlinauthservice.domain.model.response.UserLoginResponse
import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.PostgresMock
import br.com.devlhse.kotlinauthservice.utils.getResourceContent
import io.javalin.core.util.Header.AUTHORIZATION
import io.restassured.RestAssured
import io.restassured.http.ContentType.JSON
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest


@ExtendWith(ComponentTestExtension::class)
class ContactControllerComponentTest: KoinTest {

    private val baseUrl = "http://localhost:7000"
    private val validRequestContentType = "application/json"
    private val validLoginBody = "/requests/valid_body_user_request.json".getResourceContent()

    private val loginRequestSpecification: RequestSpecification = RestAssured.given()
        .baseUri(baseUrl)
        .body(validLoginBody)
        .contentType(validRequestContentType)


    private val paginatedRequestSpecification: RequestSpecification = RestAssured.given()
        .baseUri(baseUrl)



    @Test
    fun `when contact controller request to find contacts of an user should return success`() {
        val path = "find_paginated_contacts"
        PostgresMock.executeScripts("$path/001.sql")

        val jsonLoginResponse = loginRequestSpecification.post(LOGIN_ROUTE).body.asString()
        val userLoginResponse = ObjectMapperConfig.jsonObjectMapper.
        readValue(jsonLoginResponse, UserLoginResponse::class.java)
        val accessToken = userLoginResponse.accessToken

        paginatedRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().get(VALID_CONTACTS_QUERY_ROUTE)
            .then().statusCode(HttpStatus.SC_OK)
            .and().contentType(JSON)
    }


    companion object {
        private const val LOGIN_ROUTE = "/users/login"
        private const val VALID_CONTACTS_QUERY_ROUTE = "/contacts?pageNumber=1&pageSize=5"
    }
}