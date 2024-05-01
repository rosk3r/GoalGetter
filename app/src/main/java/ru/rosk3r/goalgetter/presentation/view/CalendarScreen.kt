package ru.rosk3r.goalgetter.presentation.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rosk3r.goalgetter.R
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskRequest
import ru.rosk3r.goalgetter.domain.model.Task
import ru.rosk3r.goalgetter.presentation.components.CalendarBody
import ru.rosk3r.goalgetter.presentation.components.CalendarHeader
import ru.rosk3r.goalgetter.presentation.components.MyNavigationBar
import ru.rosk3r.goalgetter.presentation.components.myToast
import ru.rosk3r.goalgetter.util.GoalGetterDatabase
import java.time.LocalDateTime

@Composable
fun CalendarScreen(navController: NavController, context: Context, database: GoalGetterDatabase) {
    val selectedTab = 2
    val coroutineScope = rememberCoroutineScope()
    val tasksState = remember { mutableStateOf(emptyList<Task>()) }

    // Load tasks asynchronously
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val tasks = withContext(Dispatchers.IO) {
                    database.taskDao().getAll().first()
                }
                tasksState.value = tasks
            } catch (e: Exception) {
                myToast(context, "something went wrong")
            }
        }
    }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .background(colorResource(id = R.color.darkBackground))
            ) {
                Text(
                    text = "Your incomplete tasks",
                    fontSize = 34.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                )
            }
        },
        bottomBar = {
            MyNavigationBar(selectedTab, navController)
        }
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.darkBackground))
                .fillMaxSize()
                .padding(it) // Use it instead of a fixed padding value
        ) {
            CalendarHeader()
            Spacer(modifier = Modifier.height(16.dp))
            CalendarBody(
                tasks = tasksState.value.toList()
            )
        }
    }
}

