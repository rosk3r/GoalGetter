package ru.rosk3r.goalgetter.presentation.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskRequest
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskStatusChangeRequest
import ru.rosk3r.goalgetter.domain.model.Task
import ru.rosk3r.goalgetter.presentation.components.ArchivedTaskList
import ru.rosk3r.goalgetter.presentation.components.MyNavigationBar
import ru.rosk3r.goalgetter.presentation.components.myToast
import ru.rosk3r.goalgetter.util.GoalGetterDatabase

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ArchiveScreen(navController: NavController, context: Context, database: GoalGetterDatabase) {
    val selectedTab = 1
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(true) }
    val tasksState = remember { mutableStateOf(emptyList<Task>()) }

    val onDelete = { taskToDelete: Task ->
        val updatedTasks = tasksState.value.filter { it.id != taskToDelete.id }
        tasksState.value = updatedTasks
    }
    val onStatus: (Task) -> Unit = { updatedTask ->
        tasksState.value = tasksState.value.map { task ->
            if (task.id == updatedTask.id) {
                updatedTask
            } else {
                task
            }
        }

        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    database.taskDao().updateStatusById(updatedTask.id, updatedTask.isCompleted)
                    withContext(Dispatchers.IO) {
                        withContext(Dispatchers.IO) {
                            val session = database.sessionDao().getOne()
                            val taskRequest = TaskStatusChangeRequest(session.token, updatedTask.id)
                            taskRequest.request(taskRequest)
                        }
                    }
                }
                isLoading.value = false
            } catch (e: Exception) {
                myToast(context, "something went wrong")
            }
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
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
                isLoading.value = false
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
                    text = "Your completed tasks",
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
            if (isLoading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(45.dp),
                        color = Color.White
                    )
                }
            } else {
                ArchivedTaskList(
                    tasksState.value.filter { it.isCompleted },
                    database,
                    onDelete,
                    onStatus,
                    context
                )
            }
        }
    }
}