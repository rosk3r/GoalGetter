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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rosk3r.goalgetter.R
import ru.rosk3r.goalgetter.domain.model.Task

@Composable
fun TaskList(tasks: List<Task>?) {
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        tasks?.forEach { task ->
            TaskItem(
                task = task,
                onDelete = { /* логика удаления задачи */ },
                onEdit = { /* логика редактирования задачи */ }
            )
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit, onEdit: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        Modifier
            .padding(bottom = 8.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(colorResource(id = R.color.background))
            .clickable { expanded = !expanded } // Toggle expanded state on click
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
                    checked = task.isCompleted,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.DarkGray,
                        checkmarkColor = colorResource(id = R.color.darkBackground)
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                )

                // Title with optional truncation
                Text(
                    text = task.title,
                    fontSize = 18.sp,
                    maxLines = if (expanded) Int.MAX_VALUE else 1, // Allow multiple lines if expanded
                    overflow = TextOverflow.Ellipsis, // Show ellipsis when text overflows
                    modifier = Modifier
                        .weight(1f) // Expand to fill available space
                )

                // Date
                Text(
                    text = task.createdAt.toLocalDate().toString(),
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            }

            // Buttons for edit and delete
            if (expanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}
