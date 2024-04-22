package ru.rosk3r.goalgetter.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rosk3r.goalgetter.R
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskDeleteRequest
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskEditRequest
import ru.rosk3r.goalgetter.data.remote.dto.request.TaskStatusChangeRequest
import ru.rosk3r.goalgetter.domain.model.Task
import ru.rosk3r.goalgetter.util.GoalGetterDatabase
import java.time.LocalDateTime

@Composable
fun TaskList(
    tasks: List<Task>?,
    database: GoalGetterDatabase,
    onDelete: (Task) -> Unit,
    onEdit: (Task) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        tasks?.forEach { task ->
            TaskItem(
                task = task,
                database = database,
                onDelete = onDelete,
                onEdit = onEdit
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    database: GoalGetterDatabase,
    onDelete: (Task) -> Unit,
    onEdit: (Task) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val openEditDialog = remember { mutableStateOf(false) }
    val openDeleteDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var isCompleted by remember { mutableStateOf(task.isCompleted) }

    Box(
        Modifier
            .padding(bottom = 8.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(colorResource(id = R.color.background))
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = {
                        isCompleted = !isCompleted
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                val session = database.sessionDao().getOne()
                                val taskRequest = TaskStatusChangeRequest(session.token, task.id)
                                val newTask = taskRequest.request(taskRequest)

                                if (newTask != null) {
                                    database.taskDao().updateStatusById(newTask.id, newTask.isCompleted)
                                }
                            }
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.DarkGray,
                        checkmarkColor = colorResource(id = R.color.darkBackground)
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                )

                Text(
                    text = task.title,
                    fontSize = 18.sp,
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = LocalDateTime.parse(task.createdAt).toLocalDate().toString(),
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            }

            if (expanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            openEditDialog.value = true
                        }
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    IconButton(
                        onClick = {
                            openDeleteDialog.value = true
                        }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
                if (openEditDialog.value) {
                    EditTaskDialog(
                        openDialog = openEditDialog, // Передаем состояние диалога
                        onEdit = { title ->
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    val session = database.sessionDao().getOne()
                                    val taskRequest = TaskEditRequest(session.token, task.id, title)
                                    taskRequest.request(taskRequest)

                                    database.taskDao().updateTitleById(task.id, title)

                                    // Сообщаем родительскому компоненту об изменении
                                    onEdit(task.copy(title = title))
                                }
                            }

                            openEditDialog.value = false // Закрываем диалог после редактирования
                            expanded = !expanded
                        },
                        onCancel = {
                            expanded = !expanded
                        }
                    )
                }

                if (openDeleteDialog.value) {
                    TaskDeleteDialog(
                        openDialog = openDeleteDialog,
                        onDelete = {
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    val session = database.sessionDao().getOne()
                                    val taskRequest = TaskDeleteRequest(session.token, task.id)
                                    taskRequest.request(taskRequest)

                                    database.taskDao().delete(task)
                                }
                            }

                            openEditDialog.value = false
                            expanded = !expanded
                            onDelete(task)
                        },
                        onCancel = {
                            expanded = !expanded
                        }
                    )
                }
            }
        }
    }
}

