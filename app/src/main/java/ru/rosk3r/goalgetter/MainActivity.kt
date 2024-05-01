package ru.rosk3r.goalgetter

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import ru.rosk3r.goalgetter.presentation.view.ArchiveScreen
import ru.rosk3r.goalgetter.presentation.view.CalendarScreen
import ru.rosk3r.goalgetter.presentation.view.GoalScreen
import ru.rosk3r.goalgetter.presentation.view.SettingsScreen
import ru.rosk3r.goalgetter.presentation.view.SignInScreen
import ru.rosk3r.goalgetter.presentation.view.SignUpScreen
import ru.rosk3r.goalgetter.presentation.view.ToDoScreen
import ru.rosk3r.goalgetter.util.GoalGetterDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarAndNavigationBarColor(Color(0xFF66756E), Color(0xFF66756E))

        setContent {
            val database = Room.databaseBuilder(
                applicationContext,
                GoalGetterDatabase::class.java, "GoalGetterDatabase"
            ).build()

            var startDestination = "signUp"
            val thread = Thread() {
                startDestination = if (database.sessionDao().hasAnyRecords()) {
                    "screen_1"
                } else {
                    "signUp"
                }
            }
            thread.start()
            thread.join()

            MyApp(startDestination, database)
        }
    }

    private fun setStatusBarAndNavigationBarColor(
        statusBarColor: Color,
        navigationBarColor: Color
    ) {
        window.statusBarColor = statusBarColor.toArgb()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = navigationBarColor.toArgb()
        }
    }
}

@Composable
fun MyApp(startDestination: String, database: GoalGetterDatabase) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination) {
        composable("signUp") {
            SignUpScreen(navController, context, database)
        }
        composable("signIn") {
            SignInScreen(navController, context, database)
        }
        composable("screen_1") {
            ToDoScreen(navController, context, database)
        }
        composable("screen_2") {
            ArchiveScreen(navController, context, database)
        }
        composable("screen_3") {
            CalendarScreen(navController, context, database)
        }
        composable("screen_4") {
            GoalScreen(navController, context, database)
        }
        composable("screen_5") {
            SettingsScreen(navController, context, database)
        }
    }
}
