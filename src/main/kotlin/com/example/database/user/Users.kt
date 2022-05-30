package com.example.database.user

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("users") {
    private val id = integer("id").autoIncrement()
    private val username = varchar("username", 50)
    private val email = varchar("email", 50)
    private val password = varchar("password", 50)
    private val token = varchar("token", 250)

    override val primaryKey = PrimaryKey(id)

    fun getUsers():List<User>{
        return try {
            transaction {
                val user = Users.selectAll().toList()
                user.map { User(
                    username = it[username],
                    email = it[email],
                    password = it[password],
                    id = it[Users.id]
                ) }
            }
        }catch (e:Exception){ emptyList() }
    }

    fun getUser(id: Int):User? {
        transaction {
            val user = Users.select { Users.id.eq(id) }
            return@transaction user.map {
                User(
                    username = it[username],
                    email = it[email],
                    password = it[password],
                    id = it[Users.id]
                )
            }
        }
        return null
    }

    fun deleteUser(id:Int){
        transaction {
            Users.deleteWhere{ Users.id.eq(id) }
        }
    }

    fun deleteAllUser(){
        transaction {
            Users.deleteAll()
        }
    }

    fun updateUser(id: Int,userDTO: UserDTO){
        transaction {
            Users.update({ Users.id.eq(id) } ){
                it[username] = userDTO.username
                it[email] = userDTO.email
                it[password] = userDTO.password
            }
        }
    }

    fun insert(userDTO: UserDTO){
        transaction {
            Users.insert {
                it[username] = userDTO.username
                it[email] = userDTO.email
                it[password] = userDTO.password
                it[token] = userDTO.token
            }
        }
    }

    fun fetchUser(email:String) : User?{
        return try {
            val userModel = Users.select { Users.email.eq(email) }.single()
            User(
                username = userModel[username],
                email = userModel[Users.email],
                password = userModel[password],
                id = userModel[id]
            )
        }catch (e:Exception){ null }
    }
}