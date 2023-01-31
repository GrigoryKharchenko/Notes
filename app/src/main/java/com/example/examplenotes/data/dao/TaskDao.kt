package com.example.examplenotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplenotes.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun subscribeToReceive(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun selectById(id: Int): TaskEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    //  TODO: передавать модель
    @Query("UPDATE task SET title = :title, description = :description WHERE id = :id ")
    suspend fun updateTask(id: Int, title: String, description: String)

    @Query("UPDATE task SET isComplete = :icComplete WHERE id = :id ")
    suspend fun updateTaskCounter(id: Int, icComplete: Boolean)
}
