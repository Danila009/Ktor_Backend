package com.example.features.user.registration

import com.example.features.user.UserController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRegistrationRouting() {

    routing {
        post(path = "/registration"){
            val userController = UserController(call)
            userController.registrationUser()
        }
    }
}