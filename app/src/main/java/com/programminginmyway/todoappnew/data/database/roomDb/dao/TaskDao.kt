package com.programminginmyway.todoappnew.data.database.roomDb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.programminginmyway.todoappnew.data.database.roomDb.model.TaskList

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: TaskList): Long

    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<TaskList>

    @Query("UPDATE task SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: Int)

    @Update
    suspend fun updateTask(task: TaskList)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun deleteTask(id: Int)
}