package com.example.database.user

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("users") {
    private val id = integer("id").autoIncrement()
    private val username = varchar("username", 50)
    private val email = varchar("email", 50)
    private val password = varchar("password", 50)

    override val primaryKey = PrimaryKey(id)

    fun getUsers():List<UserDTO>{
        return try {
            transaction {
                val user = Users.selectAll().toList()
                user.map { UserDTO(
                    username = it[username],
                    email = it[email],
                    password = it[password]
                ) }
            }
        }catch (e:Exception){ emptyList() }
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
            }
        }
    }

    fun fetchUser(email:String) : UserDTO?{
        return try {
            val userModel = Users.select { Users.email.eq(email) }.single()
            UserDTO(
                username = userModel[username],
                email = userModel[Users.email],
                password = userModel[password]
            )
        }catch (e:Exception){ null }
    }
}