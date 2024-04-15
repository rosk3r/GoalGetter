package ru.rosk3r.composetest.presentation.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.rosk3r.composetest.R
import ru.rosk3r.composetest.data.remote.dto.request.TaskRequest
import ru.rosk3r.composetest.domain.model.Task
import ru.rosk3r.composetest.presentation.components.MyNavigationBar
import ru.rosk3r.composetest.presentation.components.TaskList
import ru.rosk3r.composetest.util.GoalGetterDatabase

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToDoScreen(navController: NavController, context: Context, database: GoalGetterDatabase) {
    val selectedTab = 0
    var tasks: List<Task>? = null

    val thread = Thread {
        val session = database.sessionDao().getOne()
        val taskRequest = TaskRequest(session.token)
        tasks = taskRequest.request(taskRequest)

        tasks?.forEach { task ->
            database.taskDao().insert(task)
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
                    text = "Your To Do's",
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
        Box(
            Modifier
                .background(colorResource(id = R.color.darkBackground))
                .fillMaxSize()
                .padding(it) // Use it instead of a fixed padding value
        ) {

            thread.start()
            thread.join()
            // List of tasks
            TaskList(tasks)
        }
    }
}
