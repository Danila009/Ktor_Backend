package com.example.features.user.authorization.model

@kotlinx.serialization.Serializable
data class Authorization(
    val email:String,
    val password:String
)