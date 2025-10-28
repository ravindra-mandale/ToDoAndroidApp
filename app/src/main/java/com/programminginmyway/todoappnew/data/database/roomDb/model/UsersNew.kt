package com.programminginmyway.todoappnew.data.database.roomDb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programminginmyway.todoappnew.data.database.USERS_TABLE

@Entity(tableName = USERS_TABLE)
data class UsersNew (
    @PrimaryKey(autoGenerate = true)
    val USERID: Int = 0,
    val EMAILID: String,
    val FULLNAME: String,
    val MOBILENO: String,
    val PASSWORD: String
)
