package com.mruraza.smartstudy.data.repository

import com.mruraza.smartstudy.data.local.SessionsDAO
import com.mruraza.smartstudy.data.local.SubjectDAO
import com.mruraza.smartstudy.data.local.TaskDAO
import com.mruraza.smartstudy.domain.model.Subjects
import com.mruraza.smartstudy.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepoImpl @Inject constructor(
    private val subjectDAO: SubjectDAO,
    private val taskDAO: TaskDAO,
    private val sessionsDAO: SessionsDAO
) : SubjectRepository {
    override suspend fun upsertSubject(subjects: Subjects) {
        subjectDAO.upsertSubject(subjects)
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        return subjectDAO.getTotalSubjectCount()
    }

    override fun getTotalGoalHours(): Flow<Float> {
        return subjectDAO.getTotalGoalHour()
    }

    override suspend fun deleteSubject(subjectId: Int) {
        sessionsDAO.deleteSessionsBySubject(subjectId)
        taskDAO.deleteTaskBySubID(subjectId)
        subjectDAO.deleteSubject(subjectId)
    }

    override suspend fun getSubjectById(subjectId: Int): Subjects? {
        return subjectDAO.getSubjectById(subjectId)
    }

    override fun getAllSubjects(): Flow<List<Subjects>> {
        return subjectDAO.getAllSubjects()
    }
}