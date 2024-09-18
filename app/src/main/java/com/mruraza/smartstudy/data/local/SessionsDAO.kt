package com.mruraza.smartstudy.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mruraza.smartstudy.domain.model.Sessions
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionsDAO {

    @Insert
    suspend fun insertSessions(sessions: Sessions)

    @Delete
    suspend fun deleteSessions(sessions: Sessions)

    @Query("SELECT * FROM SESSIONS")
    fun getAllSessions():Flow<List<Sessions>>

    @Query("SELECT * FROM SESSIONS WHERE sessionSubjectId=:subjectId")
    fun getRecentSessionsForSubject(subjectId:Int) :Flow<List<Sessions>>

    @Query("SELECT SUM(duration) FROM Sessions WHERE sessionSubjectId=:subjectId")
    fun getTotalSessionsDurationBySubjectId(subjectId: Int):Flow<Long>

    @Query("SELECT SUM(duration) FROM Sessions ")
    fun getTotalSessionDuration():Flow<Long>

    @Query("DELETE FROM SESSIONS WHERE sessionSubjectId=:subjectId")
    fun deleteSessionsBySubject(subjectId: Int)

}
