package com.programminginmyway.todoappnew.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USERS")
data class UsersNew (
    @PrimaryKey(autoGenerate = true)
    val USERID: Int = 0,
    val EMAILID: String,
    val FULLNAME: String,
    val MOBILENO: String,
    val PASSWORD: String
)
