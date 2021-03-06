package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.config.ObjectMapperConfig
import br.com.devlhse.kotlinauthservice.domain.model.response.UserLoginResponse
import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.PostgresMock
import br.com.devlhse.kotlinauthservice.utils.getResourceContent
import io.javalin.core.util.Header.AUTHORIZATION
import io.restassured.RestAssured
import io.restassured.http.ContentType.JSON
import io.restassured.http.ContentType.TEXT
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest


@ExtendWith(ComponentTestExtension::class)
class ContactControllerComponentTest: KoinTest {

    private val baseUrl = "http://localhost:7000"
    private val validRequestContentType = "application/json"
    private val validLoginBody = "/requests/valid_body_user_request.json".getResourceContent()
    private val validContactBody = "/requests/valid_body_contact_request.json".getResourceContent()
    private val validUpdateContactBody = "/requests/valid_body_update_contact_request.json".getResourceContent()
    private val invalidContactBody = "/requests/invalid_body_contact_request.json".getResourceContent()
    private lateinit var accessToken: String

    private val loginRequestSpecification: RequestSpecification = RestAssured.given()
        .baseUri(baseUrl)
        .body(validLoginBody)
        .contentType(validRequestContentType)


    private val deafultRequestSpecification: RequestSpecification = RestAssured.given()
        .baseUri(baseUrl)


    private val contactRequestSpecification: RequestSpecification = RestAssured.given()
        .baseUri(baseUrl)
        .body(validContactBody)

    private val invalidContactRequestSpecification: RequestSpecification = RestAssured.given()
        .baseUri(baseUrl)
        .body(invalidContactBody)

    @BeforeEach
    fun setUp(){
        val path = "create_contact"
        PostgresMock.executeScripts("$path/001.sql")

        val jsonLoginResponse = loginRequestSpecification.post(LOGIN_ROUTE).body.asString()
        val userLoginResponse = ObjectMapperConfig.jsonObjectMapper.
        readValue(jsonLoginResponse, UserLoginResponse::class.java)
        accessToken = userLoginResponse.accessToken
    }

    @Test
    fun `when contact controller request to find contacts of an user should return success`() {
        val path = "find_paginated_contacts"
        PostgresMock.executeScripts("$path/002.sql")

        deafultRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().get(VALID_CONTACTS_QUERY_ROUTE)
            .then().statusCode(HttpStatus.SC_OK)
            .and().contentType(JSON)
    }

    @Test
    fun `when contact controller request to create a contact return created`() {

        contactRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().post(VALID_CONTACTS_ROUTE)
            .then().statusCode(HttpStatus.SC_CREATED)
            .and().contentType(TEXT)
    }

    @Test
    fun `when contact controller request to create a contact without phone should return error`() {

        invalidContactRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().post(VALID_CONTACTS_ROUTE)
            .then().statusCode(HttpStatus.SC_NOT_FOUND)
            .and().contentType(JSON)
    }

    @Test
    fun `when contact controller request to find contact by id of an user should return success`() {
        val path = "find_paginated_contacts"
        PostgresMock.executeScripts("$path/002.sql")

        deafultRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().get("$VALID_CONTACTS_ROUTE/1")
            .then().statusCode(HttpStatus.SC_OK)
            .and().contentType(JSON)
            .body("id", Matchers.equalTo(1))
            .body("name", Matchers.equalTo("teste contact 1"))
            .body("email", Matchers.equalTo("teste@teste.com.br"))
            .body("phone", Matchers.equalTo("551399177777"))
    }

    @Test
    fun `when contact controller request to find contact by id of another user should return error`() {
        val path = "find_paginated_contacts"
        PostgresMock.executeScripts("$path/002.sql")

        deafultRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().get("$VALID_CONTACTS_ROUTE/11")
            .then().statusCode(HttpStatus.SC_NOT_FOUND)
            .and().contentType(JSON)

    }

    @Test
    fun `when contact controller request to update contact by id of an user should return success`() {
        val path = "find_paginated_contacts"
        PostgresMock.executeScripts("$path/002.sql")

        deafultRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken").body(validUpdateContactBody)
            .`when`().put("$VALID_CONTACTS_ROUTE/1")
            .then().statusCode(HttpStatus.SC_NO_CONTENT)
            .and().contentType(TEXT)

        deafultRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().get("$VALID_CONTACTS_ROUTE/1")
            .then().statusCode(HttpStatus.SC_OK)
            .and().contentType(JSON)
            .body("id", Matchers.equalTo(1))
            .body("name", Matchers.equalTo("teste contact updated"))
            .body("email", Matchers.equalTo("teste@teste.com.br"))
            .body("phone", Matchers.equalTo("551399177777"))

    }

    @Test
    fun `when contact controller request to delete contact by id of an user should return success`() {
        val path = "find_paginated_contacts"
        PostgresMock.executeScripts("$path/002.sql")

        deafultRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().delete("$VALID_CONTACTS_ROUTE/1")
            .then().statusCode(HttpStatus.SC_NO_CONTENT)
            .and().contentType(TEXT)

        deafultRequestSpecification.header(AUTHORIZATION, "Bearer $accessToken")
            .`when`().get("$VALID_CONTACTS_ROUTE/1")
            .then().statusCode(HttpStatus.SC_NOT_FOUND)
            .and().contentType(JSON)

    }

    companion object {
        private const val LOGIN_ROUTE = "/users/login"
        private const val VALID_CONTACTS_QUERY_ROUTE = "/contacts?pageNumber=1&pageSize=5"
        private const val VALID_CONTACTS_ROUTE = "/contacts"
    }
}