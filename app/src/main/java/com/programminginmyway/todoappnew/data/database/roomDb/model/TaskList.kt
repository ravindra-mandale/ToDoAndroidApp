package com.programminginmyway.todoappnew.data.database.roomDb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programminginmyway.todoappnew.data.database.TASK_TABLE
import java.util.Date

@Entity(tableName = TASK_TABLE)
data class TaskList(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val status: Int,
    val taskTitle: String,
    val dateTime: Date
)