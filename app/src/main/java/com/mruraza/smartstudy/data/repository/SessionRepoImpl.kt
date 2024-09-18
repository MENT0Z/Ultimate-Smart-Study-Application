package com.mruraza.smartstudy.data.repository

import com.mruraza.smartstudy.data.local.SessionsDAO
import com.mruraza.smartstudy.domain.model.Sessions
import com.mruraza.smartstudy.domain.repository.SessionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class SessionRepoImpl @Inject constructor(
    private val sessionsDAO: SessionsDAO
):SessionRepo {
    override suspend fun insertSession(session: Sessions) {
        sessionsDAO.insertSessions(session)
    }

    override suspend fun deleteSession(session: Sessions) {
        TODO("Not yet implemented")
    }

    override fun getAllSession(): Flow<List<Sessions>> {
        TODO("Not yet implemented")
    }

    override fun getRecentFiveSessions(): Flow<List<Sessions>> {
        return sessionsDAO.getAllSessions().take(count = 5)
    }

    override fun getRecentTenSessionsForSubject(subjectId: Int): Flow<List<Sessions>> {
       return sessionsDAO.getRecentSessionsForSubject(subjectId).take(count = 10)
    }

    override fun getTotalSessionDurationBySubjectId(subjectId: Int): Flow<Long> {
        return sessionsDAO.getTotalSessionsDurationBySubjectId(subjectId)
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionsDAO.getTotalSessionDuration()
    }

    override fun deleteSessionBySubject(subjectId: Int) {
        TODO("Not yet implemented")
    }
}