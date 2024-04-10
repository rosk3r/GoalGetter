package ru.rosk3r.composetest

import ru.rosk3r.composetest.presentation.view.SignInScreen
import ru.rosk3r.composetest.presentation.view.SignUpScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.rosk3r.composetest.presentation.view.ArchiveScreen
import ru.rosk3r.composetest.presentation.view.CalendarScreen
import ru.rosk3r.composetest.presentation.view.GoalScreen
import ru.rosk3r.composetest.presentation.view.SettingsScreen
import ru.rosk3r.composetest.presentation.view.ToDoScreen

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

    NavHost(navController = navController, startDestination) {
        composable("signUp") {
            SignUpScreen(navController)
        }
        composable("signIn") {
            SignInScreen(navController)
        }
        composable("screen_1") {
            ToDoScreen(navController = navController)
        }
        composable("screen_2") {
            ArchiveScreen(navController = navController)
        }
        composable("screen_3") {
            CalendarScreen(navController = navController)
        }
        composable("screen_4") {
            GoalScreen(navController = navController)
        }
        composable("screen_5") {
            SettingsScreen(navController = navController)
        }
    }
}

