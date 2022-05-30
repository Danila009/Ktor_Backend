package com.example.features.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.database.user.UserDTO
import com.example.database.user.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class UserController(
    private val call: ApplicationCall
) {
    suspend fun registrationUser(
        secret: String, issuer: String, audience: String
    ){
        val user = call.receive<UserDTO>()
        val userDTO = Users.fetchUser(user.email)
        if(userDTO == null){

            val longExpireAfter1Hr = System.currentTimeMillis() + 36_00_000

            val generatedToken = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.username)
                .withExpiresAt(Date(longExpireAfter1Hr))
                .sign(Algorithm.HMAC512(secret))

            user.token = generatedToken
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