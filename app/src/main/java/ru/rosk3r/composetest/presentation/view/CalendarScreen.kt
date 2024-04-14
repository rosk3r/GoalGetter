package ru.rosk3r.composetest.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.rosk3r.composetest.R
import ru.rosk3r.composetest.domain.model.Task
import ru.rosk3r.composetest.presentation.components.MyNavigationBar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(navController: NavController) {
    val selectedTab = 2

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
fun CalendarHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val currentMonth = remember { LocalDateTime.now().month }
        val monthName = currentMonth.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val currentYear = remember { LocalDate.now().year }

        Text(
            text = "$monthName $currentYear",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 16.dp)
        )
        IconButton(
            onClick = { /* Handle calendar navigation */ },
            modifier = Modifier.padding(end = 16.dp)
        ) {
//            Icon(Icons.Filled.CalendarToday, contentDescription = "Calendar")
        }
    }
}

@Composable
fun calculateBackgroundColor(taskCount: Int, isCurrentMonth: Boolean): Color {
    return if (isCurrentMonth) {
        when {
            taskCount > 3 -> colorResource(R.color.accent)
            taskCount > 0 -> colorResource(R.color.background)
            else -> Color.Transparent
        }
    } else {
        Color.Gray
    }
}


@Composable
fun CalendarBody(tasks: List<Task>) {
    val today = remember { LocalDate.now() }
    val yearMonth = YearMonth.now()
    val weeksInMonth = yearMonth.lengthOfMonth() / 7

    val tasksByDate = tasks.groupBy { it.createdAt }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(weeksInMonth) { weekIndex ->
            Row {
                repeat(7) { dayIndex ->
                    val day = weekIndex * 7 + dayIndex + 1
                    val date = yearMonth.atDay(day)
                    val isCurrentMonth = date.month == yearMonth.month
                    val taskCount = tasksByDate[date]?.count { !it.isCompleted } ?: 0

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(calculateBackgroundColor(taskCount, isCurrentMonth)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            fontSize = 16.sp,
                            color = if (date == today) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}
