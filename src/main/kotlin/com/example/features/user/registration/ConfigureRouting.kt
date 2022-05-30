package com.example.features.user.registration

import com.example.features.user.UserController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRegistrationRouting() {

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    routing {
        post(path = "/registration"){
            val userController = UserController(call)
            userController.registrationUser(
                secret, issuer, audience
            )
        }
    }
}