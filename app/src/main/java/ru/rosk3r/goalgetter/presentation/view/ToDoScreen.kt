package ru.rosk3r.goalgetter.presentation.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch
import ru.rosk3r.goalgetter.R
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskCreateRequest
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskDeleteRequest
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskRequest
import ru.rosk3r.goalgetter.domain.model.Task
import ru.rosk3r.goalgetter.presentation.components.MyNavigationBar
import ru.rosk3r.goalgetter.presentation.components.NewTaskDialog
import ru.rosk3r.goalgetter.presentation.components.TaskList
import ru.rosk3r.goalgetter.util.GoalGetterDatabase

@OptIn(ExperimentalComposeUiApi::class, kotlinx.coroutines.DelicateCoroutinesApi::class)
@Composable
fun ToDoScreen(navController: NavController, context: Context, database: GoalGetterDatabase) {
    val selectedTab = 0
    val coroutineScope = rememberCoroutineScope()

    val tasksState = remember { mutableStateOf(emptyList<Task>()) }
    val openDialog = remember { mutableStateOf(false) }

    val onDelete = { taskToDelete: Task ->
        val updatedTasks = tasksState.value.filter { it.id != taskToDelete.id }
        tasksState.value = updatedTasks
    }

    // Load tasks asynchronously
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val thread = Thread {
                val session = database.sessionDao().getOne()
                val taskRequest = TaskRequest(session.token)
                val tasks = taskRequest.request(taskRequest)?.reversed()

                tasks?.let {
                    tasksState.value = it
                    // Update local database
                    database.taskDao().deleteAll()

                    tasks?.forEach { task ->
                        database.taskDao().insert(task)
                    }
                }
            }
            thread.start()
            thread.join()
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
                .padding(it)
        ) {
            TaskList(tasksState.value, database, onDelete)

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
            coroutineScope.launch {
                val thread = Thread {
                    val session = database.sessionDao().getOne()
                    val taskRequest = TaskCreateRequest(session.token, title)
                    val task = taskRequest.request(taskRequest)

                    task?.let {
                        // Create a new list with the new task at the beginning
                        val updatedTasks = listOf(it) + tasksState.value
                        // Update the state to trigger recomposition
                        tasksState.value = updatedTasks
                        // Save to the local database
                        database.taskDao().insert(it)
                    }
                }
                thread.start()
                thread.join()
            }
        }
    )
}
