package ru.rosk3r.goalgetter.presentation.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.rosk3r.goalgetter.R
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskRequest
import ru.rosk3r.goalgetter.domain.model.Task
import ru.rosk3r.goalgetter.presentation.components.MyNavigationBar
import ru.rosk3r.goalgetter.presentation.components.NewTaskDialog
import ru.rosk3r.goalgetter.presentation.components.TaskList
import ru.rosk3r.goalgetter.util.GoalGetterDatabase

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToDoScreen(navController: NavController, context: Context, database: GoalGetterDatabase) {
    val selectedTab = 0
    var tasks: List<Task>? = null
    val openDialog = remember { mutableStateOf(false) }

    val thread = Thread {
        val session = database.sessionDao().getOne()
        val taskRequest = TaskRequest(session.token)
        tasks = taskRequest.request(taskRequest)
        database.taskDao().deleteAll()

        tasks?.forEach { task ->
            database.taskDao().insert(task)
        }
    }
    thread.start()
    thread.join()

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
            // List of tasks
            TaskList(tasks)

            FloatingActionButton(
                onClick = {
                    openDialog.value = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                contentColor = colorResource(id = R.color.white),
                containerColor = colorResource(id = R.color.background),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Task",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
    NewTaskDialog(
        openDialog = openDialog,
        onSave = { title ->
            // Ваша логика сохранения новой задачи
        }
    )
}
