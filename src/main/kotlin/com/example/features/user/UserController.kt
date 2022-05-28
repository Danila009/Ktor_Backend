package com.example.features.user

import com.example.database.user.UserDTO
import com.example.database.user.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UserController(
    private val call: ApplicationCall
) {
    suspend fun registrationUser(){
        val user = call.receive<UserDTO>()
        val userDTO = Users.fetchUser(user.email)
        if(userDTO == null){
            Users.insert(userDTO = user)
            call.respond(HttpStatusCode.OK)
        }else{
            call.respond(HttpStatusCode.NotFound, "email занят")
        }
    }

    suspend fun getUsers(){
        val users = Users.getUsers()
        call.respond(users)
    }

    suspend fun deleteUser(id: Int){
        try {
            Users.deleteUser(id)
            call.respond(HttpStatusCode.OK)
        }catch (e:Exception){
            call.respond(HttpStatusCode.NotFound)
        }
    }

    suspend fun deleteAllUser(){
        try {
            Users.deleteAllUser()
            call.respond(HttpStatusCode.OK)
        }catch (e:Exception){
            call.respond(HttpStatusCode.NotFound)
        }
    }

    suspend fun updateUser(id: Int){
        try {
            val user = call.receive<UserDTO>()
            Users.updateUser(id, user)
            call.respond(HttpStatusCode.OK)
        }catch (e:Exception){
            call.respond(HttpStatusCode.NotFound)
        }
    }
}