package br.com.devlhse.kotlinauthservice.application.web.routes

import br.com.devlhse.kotlinauthservice.application.web.controllers.ContactController
import br.com.devlhse.kotlinauthservice.config.Roles
import io.javalin.apibuilder.ApiBuilder
import io.javalin.core.security.SecurityUtil.roles

object ContactRoutes {
    fun init() {
        ApiBuilder.path("contacts") {
            ApiBuilder.get(ContactController::findPaginatedContactsbyUser, roles(Roles.AUTHENTICATED))
            ApiBuilder.post(ContactController::createContact, roles(Roles.AUTHENTICATED))
            ApiBuilder.path(":contact-id") {
                ApiBuilder.get(ContactController::getContactById, roles(Roles.AUTHENTICATED))
//                ApiBuilder.patch(ContactController::updateContact, roles(Roles.AUTHENTICATED))
//                ApiBuilder.delete(ContactController::deleteContact, roles(Roles.AUTHENTICATED))
            }
        }
    }
}
