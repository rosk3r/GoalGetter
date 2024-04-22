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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rosk3r.goalgetter.R
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskCreateRequest
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

    val onEdit = { editedTask: Task ->
        val updatedTasks = tasksState.value.map { task ->
            if (task.id == editedTask.id) {
                editedTask
            } else {
                task
            }
        }
        tasksState.value = updatedTasks
    }
    val onDelete = { taskToDelete: Task ->
        val updatedTasks = tasksState.value.filter { it.id != taskToDelete.id }
        tasksState.value = updatedTasks
    }


    // Load tasks asynchronously
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val tasks = withContext(Dispatchers.IO) {
                val session = database.sessionDao().getOne()
                val taskRequest = TaskRequest(session.token)
                taskRequest.request(taskRequest)?.reversed()
            }

            tasks?.let {
                tasksState.value = it
                withContext(Dispatchers.IO) {
                    database.taskDao().deleteAll()

                    it.forEach { task ->
                        database.taskDao().insert(task)
                    }
                }
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
            TaskList(tasksState.value, database, onDelete, onEdit)

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
                withContext(Dispatchers.IO) {
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
            }
        }
    )
}
