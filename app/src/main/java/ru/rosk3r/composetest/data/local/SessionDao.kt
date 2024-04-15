package ru.rosk3r.composetest.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.rosk3r.composetest.domain.model.Session

@Dao
interface SessionDao {

    @Query("SELECT * FROM Session")
    fun getAll(): Flow<List<Session>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(session: Session)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(session: Session)

    @Delete
    suspend fun delete(session: Session)

}