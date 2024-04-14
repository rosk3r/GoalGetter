package ru.rosk3r.composetest.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.rosk3r.composetest.R
import ru.rosk3r.composetest.domain.model.Task
import ru.rosk3r.composetest.presentation.components.MyNavigationBar
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToDoScreen(navController: NavController) {
    val selectedTab = 0

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
            TaskList(
                tasks = listOf(
                    Task(title = "Task 1", isCompleted = false, createdAt = LocalDate.now()),
                    Task(
                        title = "Task 2 with a long title to demonstrate truncationTask 2 with a long title to demonstrate truncationTask 2 with a long title to demonstrate truncationTask 2 with a long title to demonstrate truncationTask 2 with a long title to demonstrate truncationTask 2 with a long title to demonstrate truncationTask 2 with a long title to demonstrate truncationTask 2 with a long title to demonstrate truncation",
                        isCompleted = true, createdAt = LocalDate.now()
                    ),
                    Task(title = "Task 3", isCompleted = false, createdAt = LocalDate.now())
                )
            )
        }
    }
}

@Composable
fun TaskList(tasks: List<Task>) {
    Column(
        modifier = Modifier

    ) {
        tasks.forEach { task ->
            TaskItem(task = task)
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        Modifier
            .padding(bottom = 8.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(colorResource(id = R.color.background))
            .clickable { expanded = !expanded } // Toggle expanded state on click
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
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
                text = task.createdAt.toString(),
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
        }
    }
}
