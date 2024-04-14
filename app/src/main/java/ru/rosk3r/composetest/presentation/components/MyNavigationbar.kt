package ru.rosk3r.composetest.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.rosk3r.composetest.R

@Composable
fun MyNavigationBar(
    selectedTab: Int,
    navController: NavController,
) {
    val tabs = listOf(
        "To Do's" to R.drawable.todo,
        "Archive" to R.drawable.archive,
        "Calendar" to R.drawable.calendar,
        "Goals" to R.drawable.goal,
        "Settings" to R.drawable.settings
    )

    BottomAppBar(
        containerColor = colorResource(id = R.color.background),
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .height(60.dp),
        content = {
            Row(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(top = 2.dp)
                    .padding(bottom = 4.dp)
                    .height(54.dp) // Increase height to accommodate icon and text
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                tabs.forEachIndexed { index, (title, icon) ->
                    BottomNavigationItem(
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .size(58.dp)
                                    .clip(shape = RoundedCornerShape(30.dp))
                                    .background(
                                    color = if (selectedTab == index) {
                                        colorResource(id = R.color.darkBackground)
                                    } else {
                                        colorResource(id = android.R.color.transparent)
                                    }
                                )
                            ) {

                                Icon(
                                    painter = painterResource(icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(26.dp)
                                )
                                Text(
                                    text = title,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(top = 3.dp) // Add some padding between icon and text
                                )
                            }
                        },
                        selected = selectedTab == index,
                        onClick = {
                            navController.navigate("screen_${index + 1}")
                        }
                    )
                }
            }
        }
    )
}
