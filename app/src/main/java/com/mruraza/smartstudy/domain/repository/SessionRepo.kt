package com.mruraza.smartstudy.domain.repository


import com.mruraza.smartstudy.domain.model.Sessions
import kotlinx.coroutines.flow.Flow

interface SessionRepo {


    suspend fun insertSession(session: Sessions)


    suspend fun deleteSession(session: Sessions)


    fun getAllSession(): Flow<List<Sessions>>

    fun getRecentFiveSessions():Flow<List<Sessions>>

    fun getRecentTenSessionsForSubject(subjectId:Int) : Flow<List<Sessions>>


    fun getTotalSessionDurationBySubjectId(subjectId: Int): Flow<Long>

    fun getTotalSessionDuration(): Flow<Long>

    fun deleteSessionBySubject(subjectId: Int)


}