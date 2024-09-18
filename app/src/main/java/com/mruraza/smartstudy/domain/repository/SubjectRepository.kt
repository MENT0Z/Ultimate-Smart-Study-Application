package com.mruraza.smartstudy.domain.repository

import com.mruraza.smartstudy.domain.model.Subjects
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    suspend fun upsertSubject(subjects: Subjects)

    fun getTotalSubjectCount():Flow<Int>

    fun getTotalGoalHours():Flow<Float>

    suspend fun deleteSubject(subjectId:Int)

    suspend fun getSubjectById(subjectId: Int):Subjects?

    fun getAllSubjects():Flow<List<Subjects>>
}