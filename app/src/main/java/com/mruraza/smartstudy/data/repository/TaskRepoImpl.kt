package com.mruraza.smartstudy.data.repository

import com.mruraza.smartstudy.data.local.TaskDAO
import com.mruraza.smartstudy.domain.model.Task
import com.mruraza.smartstudy.domain.repository.TaskRepo
import com.mruraza.smartstudy.tasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepoImpl @Inject constructor(
    private val taskDAO: TaskDAO
) : TaskRepo {
    override suspend fun upsertTask(task: Task) {
        taskDAO.upsertTask(task)
    }

    override suspend fun deleteTask(taskID: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun getUpcomingTaskForSubjects(subjectId: Int): Flow<List<Task>> {
        return taskDAO.getTaskForSubjects(subjectId)
            .map { subjects -> subjects.filter { it.isCompleted.not() } }
            .map { tasks -> sortTask(tasks) }
    }

    override fun getCompletedTaskForSubjects(subjectId: Int): Flow<List<Task>> {
        return taskDAO.getTaskForSubjects(subjectId)
            .map { subjects -> subjects.filter { it.isCompleted } }
            .map { tasks -> sortTask(tasks) }
    }

    override fun getAllUpcomingTask(): Flow<List<Task>> {
        return taskDAO.getAllTask()
            .map { tasks -> tasks.filter { it.isCompleted.not() } }
            .map { tasks -> sortTask(tasks) }

    }

    private fun sortTask(task: List<Task>): List<Task> {
        return task.sortedWith(compareBy<Task> { it.dueDate }.thenByDescending { it.priority })
    }
}