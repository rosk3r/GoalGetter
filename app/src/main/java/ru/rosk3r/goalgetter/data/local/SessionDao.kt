package ru.rosk3r.goalgetter.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.domain.model.Session

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

    @Transaction
    @Query("DELETE FROM t_sessions")
    fun deleteAll()

}