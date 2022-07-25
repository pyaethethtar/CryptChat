package com.example.cryptchat.persistence.daos

import androidx.room.*
import com.example.cryptchat.data.vos.UserVO

@Dao
interface UserDao {

    @Query("Select * From users")
    fun getAllUsers() : List<UserVO>

    @Query("Select * From users Where userId=:id")
    fun getUserById(id: String) : UserVO

    @Insert
    fun addAllUsers(user: List<UserVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserVO)

    @Query("Delete From users")
    fun deleteAllUsers()

    @Delete
    fun deleteUser(user: UserVO)
}