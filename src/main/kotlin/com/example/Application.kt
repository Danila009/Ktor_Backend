package com.example

import com.example.database.DatabaseFactory
import com.example.features.user.authorization.configureAuthorizationRouting
import com.example.features.user.configureUserRouting
import com.example.features.user.registration.configureRegistrationRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.serialization.configureSerialization

fun main() {

    embeddedServer(Netty, port = 3306, host = "0.0.0.0") {
        DatabaseFactory.init()
        configureUserRouting()
        configureRegistrationRouting()
        configureAuthorizationRouting()
        configureSerialization()
    }.start(wait = true)
}
