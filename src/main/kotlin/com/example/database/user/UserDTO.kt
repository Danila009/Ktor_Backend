package com.example.database.user

@kotlinx.serialization.Serializable
class UserDTO(
    val username:String,
    val email:String,
    val password:String
)