package ru.rosk3r.goalgetter.data.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.domain.model.Session

interface SessionRepository {

    fun getAllSessionStream(): Flow<List<Session>>
    fun getSessionStream(id: Long): Flow<Session?>
    fun hasAnyRecords(): Boolean
    fun getOne(): Session
    suspend fun insertSession(session: Session)
    suspend fun deleteSession(session: Session)
    suspend fun updateSession(session: Session)

}