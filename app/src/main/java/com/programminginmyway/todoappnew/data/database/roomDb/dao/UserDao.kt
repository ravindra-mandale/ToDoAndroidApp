package com.programminginmyway.todoappnew.data.database.roomDb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.programminginmyway.todoappnew.data.database.roomDb.model.UsersNew

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UsersNew) : Long

    @Query("SELECT * FROM USERS")
    suspend fun getAllUsers(): List<UsersNew>

    @Query("SELECT * FROM USERS WHERE EMAILID = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UsersNew?

}