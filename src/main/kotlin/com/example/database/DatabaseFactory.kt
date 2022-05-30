package com.example.database

import com.example.database.user.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(){
        val hostname = "cfif31.ru"
        val databaseName = "ISPr24-39_BeluakovDS_KtorTest"
        val username = "ISPr24-39_BeluakovDS"
        val password = "ISPr24-39_BeluakovDS"

        val database = Database.connect(
            "jdbc:mysql://$hostname:3306/$databaseName?serverTimezone=UTC&useSSL=false",
            password = password,
            user = username
        )

        transaction(database) {
            SchemaUtils.create(Users)
        }
    }
}