package com.mruraza.smartstudy.domain.repository

import com.mruraza.smartstudy.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepo {

    suspend fun upsertTask(task: Task)


    suspend fun deleteTask(taskID:Int)


    //suspend fun deleteTaskBySubID(subjectId:Int)


    suspend fun getTaskById(taskId:Int): Task?


    fun getUpcomingTaskForSubjects(subjectId: Int): Flow<List<Task>>

    fun getCompletedTaskForSubjects(subjectId: Int) :Flow<List<Task>>

    fun getAllUpcomingTask(): Flow<List<Task>>
}