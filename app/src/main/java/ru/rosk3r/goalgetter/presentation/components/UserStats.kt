package ru.rosk3r.goalgetter.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rosk3r.goalgetter.R
import ru.rosk3r.goalgetter.data.remote.dto.response.UserStatsResponse

@Composable
fun UserStatsList(
    userStatsResponse: List<UserStatsResponse>,
) {
    Column(
        modifier = Modifier
            .verticalScroll(ScrollState(0))
            .padding(bottom = 78.dp)
    ) {
        userStatsResponse?.forEach { userStatsResponse ->
            UserStatsItem(
                userStatsResponse = userStatsResponse,
            )
        }
    }
}

@Composable
fun UserStatsItem(
    userStatsResponse: UserStatsResponse
) {
    Box(
        Modifier
            .padding(bottom = 8.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(colorResource(id = R.color.background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (userStatsResponse.rank < 50) {

                    val rankIcon = when (userStatsResponse.rank.toInt()) {
                        1 -> painterResource(id = R.drawable.first)
                        2 -> painterResource(id = R.drawable.second)
                        3 -> painterResource(id = R.drawable.third)
                        else -> null
                    }

                    rankIcon?.let { painter ->
                        Icon(
                            painter = painter,
                            contentDescription = "Rank ${userStatsResponse.rank}",
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(26.dp)
                        )
                    }

                    if (userStatsResponse.rank > 3) {
                        Text(
                            text = userStatsResponse.rank.toString(),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(start = 24.dp, end = 8.dp)
                        )
                    }

                    Text(
                        text = userStatsResponse.username,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(start = 28.dp)
                            .weight(1f)
                    )

                    Text(
                        text = "${userStatsResponse.completedTasksCount}",
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
            }
        }
    }
}
