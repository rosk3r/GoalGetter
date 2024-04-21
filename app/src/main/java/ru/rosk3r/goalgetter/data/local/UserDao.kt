package ru.rosk3r.goalgetter.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.domain.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM t_users")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM t_users WHERE id = :id")
    fun getById(id: Long): Flow<User>

    @Insert()
    fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

}