//package ru.rosk3r.composetest.util
//
//import android.annotation.SuppressLint
//import android.content.ContentValues
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.remember
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModelProvider
//
//class DatabaseManager(
//    private val context: Context,
//    private val factory: SQLiteDatabase.CursorFactory?
//) :
//    SQLiteOpenHelper(context, "app", factory, 1) {
//    override fun onCreate(db: SQLiteDatabase?) {
//        val query = "CREATE TABLE token (id INTEGER PRIMARY KEY, token TEXT)"
//        db?.execSQL(query)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS token")
//        onCreate(db)
//    }
//
//    fun addToken(token: String) {
//        val values = ContentValues()
//        values.put("token", token)
//        val db = this.writableDatabase
//        db.insert("token", null, values)
//        db.close()
//    }
//
//    fun hasToken(): Boolean {
//        val db = this.readableDatabase
//        val result = db.rawQuery("SELECT * FROM token", null)
//        val hasRows = result.count > 0
//        result.close()
//        return hasRows
//    }
//
//    @SuppressLint("Range")
//    fun getToken(): String? {
//        val db = this.readableDatabase
//        val result = db.rawQuery("SELECT token FROM token", null)
//
//        var token: String? = null
//
//        if (result.moveToFirst()) {
//            token = result.getString(result.getColumnIndex("token"))
//        }
//
//        result.close() // Не забудьте закрыть Cursor
//
//        return token
//    }
//}
//
//@Composable
//fun DatabaseManagerViewModel(
//    context: Context,
//    factory: SQLiteDatabase.CursorFactory?
//): DatabaseManagerViewModel {
//    val databaseManager = remember { DatabaseManager(context, factory) }
//    return viewModel(factory = DatabaseManagerViewModelFactory(databaseManager))
//}
//
//class DatabaseManagerViewModelFactory(private val databaseManager: DatabaseManager) : ViewModelProvider.Factory {
//    @SuppressWarnings("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DatabaseManagerViewModel::class.java)) {
//            return DatabaseManagerViewModel(databaseManager) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//
//@Composable
//fun observeHasToken(databaseManagerViewModel: DatabaseManagerViewModel): Boolean {
//    val hasToken: Boolean by databaseManagerViewModel.hasToken.observeAsState(false)
//    return hasToken
//}
//
//@Composable
//fun observeToken(databaseManagerViewModel: DatabaseManagerViewModel): String? {
//    val token: String? by databaseManagerViewModel.token.observeAsState(null)
//    return token
//}
//
//@Composable
//fun DatabaseManagerProvider(
//    context: Context,
//    factory: SQLiteDatabase.CursorFactory?,
//    content: @Composable () -> Unit
//) {
//    val databaseManagerViewModel: DatabaseManagerViewModel = DatabaseManagerViewModel(context, factory)
//    Providers(DatabaseManagerViewModelAmbient provides databaseManagerViewModel) {
//        content()
//    }
//}
