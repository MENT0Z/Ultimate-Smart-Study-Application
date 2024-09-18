package com.mruraza.smartstudy.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mruraza.smartstudy.domain.model.Subjects
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDAO {

    @Upsert
    suspend fun upsertSubject(subject: Subjects)

    @Query("SELECT COUNT(*) FROM SUBJECTS")
    fun getTotalSubjectCount(): Flow<Int>

    @Query("SELECT SUM(goalHours) FROM SUBJECTS")
    fun getTotalGoalHour(): Flow<Float>

    @Query("SELECT * FROM SUBJECTS WHERE subjectId = :subjectId")
    suspend fun getSubjectById(subjectId: Int): Subjects?

    @Query("DELETE FROM SUBJECTS WHERE subjectId = :subjectId")
    suspend fun deleteSubject(subjectId: Int)

    @Query("SELECT * FROM SUBJECTS")
    fun getAllSubjects(): Flow<List<Subjects>>
}