package com.example.features.user

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUserRouting(){

    routing {
        get(path = "/users") {
            val userController = UserController(call)
            userController.getUsers()
        }

        delete(path = "/user/{id}") {
            val userController = UserController(call)
            userController.deleteUser(
                id = call.parameters["id"]!!.toInt()
            )
        }

        delete(path = "/users") {
            val userController = UserController(call)
            userController.deleteAllUser()
        }

        put(path = "/user/{id}") {
            val userController = UserController(call)
            userController.updateUser(
                id = call.parameters["id"]!!.toInt()
            )
        }
    }
}