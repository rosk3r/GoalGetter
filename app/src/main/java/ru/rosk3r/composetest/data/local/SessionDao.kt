package ru.rosk3r.composetest.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.rosk3r.composetest.domain.model.Session
import ru.rosk3r.composetest.domain.model.Task

@Dao
interface SessionDao {

    @Query("SELECT * FROM t_sessions")
    fun getAll(): Flow<List<Session>>

    @Query("SELECT * FROM t_sessions WHERE id = :id")
    fun getById(id: Long): Flow<Session>

    @Query("SELECT EXISTS(SELECT 1 FROM t_sessions LIMIT 1)")
    fun hasAnyRecords(): Boolean

    @Query("SELECT * FROM t_sessions LIMIT 1")
    fun getOne(): Session

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(session: Session)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(session: Session)

    @Delete
    suspend fun delete(session: Session)

}