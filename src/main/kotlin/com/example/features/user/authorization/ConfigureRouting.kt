package com.example.features.user.authorization

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAuthorizationRouting(
) {

    routing {

        post(path = "/authorization"){

        }
    }
}