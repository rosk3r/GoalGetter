package ru.rosk3r.composetest.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.rosk3r.composetest.data.local.SessionDao
import ru.rosk3r.composetest.data.local.TaskDao
import ru.rosk3r.composetest.data.local.UserDao
import ru.rosk3r.composetest.domain.model.Session
import ru.rosk3r.composetest.domain.model.Task
import ru.rosk3r.composetest.domain.model.User

@Database(entities = [Session::class, Task::class, User::class], version = 1)
abstract class GoalGetterDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        var INSTANCE: GoalGetterDatabase? = null
        fun getDatabase(context: Context): GoalGetterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GoalGetterDatabase::class.java,
                    name = "db_goalgetter"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}