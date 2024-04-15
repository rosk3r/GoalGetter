package ru.rosk3r.composetest

import ru.rosk3r.composetest.presentation.view.SignInScreen
import ru.rosk3r.composetest.presentation.view.SignUpScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import ru.rosk3r.composetest.presentation.view.ArchiveScreen
import ru.rosk3r.composetest.presentation.view.CalendarScreen
import ru.rosk3r.composetest.presentation.view.GoalScreen
import ru.rosk3r.composetest.presentation.view.SettingsScreen
import ru.rosk3r.composetest.presentation.view.ToDoScreen
import ru.rosk3r.composetest.util.GoalGetterDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarAndNavigationBarColor(Color(0xFF66756E), Color(0xFF66756E))

        setContent {
            val startDestination = "signUp"
            MyApp(startDestination)
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
fun MyApp(startDestination: String) {
    val navController = rememberNavController()
    val database = provideDatabase()

    NavHost(navController = navController, startDestination) {
        composable("signUp") {
            SignUpScreen(navController, database)
        }
        composable("signIn") {
            SignInScreen(navController)
        }
        composable("screen_1") {
            ToDoScreen(navController)
        }
        composable("screen_2") {
            ArchiveScreen(navController)
        }
        composable("screen_3") {
            CalendarScreen(navController)
        }
        composable("screen_4") {
            GoalScreen(navController)
        }
        composable("screen_5") {
            SettingsScreen(navController)
        }
    }
}

@Composable
fun provideDatabase(): GoalGetterDatabase {
    val context = LocalContext.current
    return remember {
        Room.databaseBuilder(
            context.applicationContext,
            GoalGetterDatabase::class.java,
            "app_database"
        ).build()
    }
}

