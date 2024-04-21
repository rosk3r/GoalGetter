package ru.rosk3r.goalgetter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.rosk3r.goalgetter.R

@Composable
fun MyAlertDialog() {
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Подтверждение действия") },
            text = { Text("Вы действительно хотите удалить выбранный элемент?") },
            confirmButton = {
                Button({ openDialog.value = false }) {
                    Text("OK", fontSize = 22.sp)
                }
            }
        )
    }
}

@Composable
fun NewTaskDialog(
    openDialog: MutableState<Boolean>,
    onSave: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }

    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false }
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(colorResource(id = R.color.darkBackground))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(colorResource(id = R.color.darkBackground))
                ) {
                    Text(
                        text = "Create new task", fontWeight = FontWeight.Bold,
                        fontSize = 18.sp, color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.DarkGray,
                            focusedLabelColor = Color.DarkGray,
                            cursorColor = Color.DarkGray,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = colorResource(R.color.background),
                            focusedTextColor = Color.Black,
                            focusedContainerColor = colorResource(R.color.accent),
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                onSave(title)
                                openDialog.value = false
                                title = ""
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF97aba1)
                            )
                        ) {
                            Text("Save")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { openDialog.value = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF97aba1)
                            )
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EditTaskDialog(
    openDialog: MutableState<Boolean>,
    onEdit: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }

    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false }
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(colorResource(id = R.color.darkBackground))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(colorResource(id = R.color.darkBackground))
                ) {
                    Text(
                        text = "Enter new title", fontWeight = FontWeight.Bold,
                        fontSize = 18.sp, color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.DarkGray,
                            focusedLabelColor = Color.DarkGray,
                            cursorColor = Color.DarkGray,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = colorResource(R.color.background),
                            focusedTextColor = Color.Black,
                            focusedContainerColor = colorResource(R.color.accent),
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                onEdit(title)
                                openDialog.value = false
                                title = ""
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF97aba1)
                            )
                        ) {
                            Text("Save")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { openDialog.value = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF97aba1)
                            )
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}
