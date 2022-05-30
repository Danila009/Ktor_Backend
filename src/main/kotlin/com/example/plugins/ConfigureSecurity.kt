package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

data class GenericResponse<out T>(val isSuccess: Boolean, val data: T)

fun Application.configureSecurity(){
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication){
        jwt {
            realm = myRealm

            verifier(
                JWT.require(Algorithm.HMAC512(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )

            validate { jwtCredential: JWTCredential ->
                kotlin.run {
                    val userName = jwtCredential.payload.getClaim("username").asString()
                    if (userName.isNotEmpty()) {
                        JWTPrincipal(jwtCredential.payload)
                    } else {
                        null
                    }
                }
            }

            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    GenericResponse(isSuccess = true, data = "Token is not valid or has expired")
                )
            }
        }
    }
}