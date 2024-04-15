package ru.rosk3r.composetest.data.remote.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.composetest.domain.model.Session
import ru.rosk3r.composetest.domain.model.Task

interface SessionRepository {

    fun getAllSessionStream(): Flow<List<Session>>
    fun getSessionStream(id: Long): Flow<Session?>
    fun hasAnyRecords(): Boolean
    fun getOne(): Session
    suspend fun insertSession(session: Session)
    suspend fun deleteSession(session: Session)
    suspend fun updateSession(session: Session)

}