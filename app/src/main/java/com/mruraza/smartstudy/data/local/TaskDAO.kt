package com.mruraza.smartstudy.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mruraza.smartstudy.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Upsert
    suspend fun upsertTask(task:Task)

    @Query("DELETE FROM TASK WHERE taskID=:taskID")
    suspend fun deleteTask(taskID:Int)

    @Query("DELETE FROM TASK WHERE taskSubjectId =:subjectId")
    suspend fun deleteTaskBySubID(subjectId:Int)

    @Query("SELECT * FROM TASK WHERE taskID =:taskId")
    suspend fun getTaskById(taskId:Int):Task?

    @Query("SELECT *FROM TASK WHERE taskSubjectId=:subjectId")
    fun getTaskForSubjects(subjectId: Int):Flow<List<Task>>

    @Query("SELECT *FROM TASK")
    fun getAllTask():Flow<List<Task>>

}