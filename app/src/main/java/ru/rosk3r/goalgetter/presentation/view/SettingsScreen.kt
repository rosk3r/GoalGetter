package ru.rosk3r.goalgetter.presentation.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rosk3r.goalgetter.R
import ru.rosk3r.goalgetter.data.remote.dto.request.PasswordChangeRequest
import ru.rosk3r.goalgetter.data.remote.dto.request.SignOutRequest
import ru.rosk3r.goalgetter.data.remote.dto.request.UsernameChangeRequest
import ru.rosk3r.goalgetter.presentation.components.MyNavigationBar
import ru.rosk3r.goalgetter.presentation.components.myToast
import ru.rosk3r.goalgetter.util.GoalGetterDatabase

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(navController: NavController, context: Context, database: GoalGetterDatabase) {
    val coroutineScope = rememberCoroutineScope()

    var changeLoginExpanded by remember { mutableStateOf(false) }
    var changePasswordExpanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var newUsername by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .background(colorResource(id = R.color.darkBackground))
            ) {
                Text(
                    text = "Settings",
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
            MyNavigationBar(selectedTab = 4, navController = navController)
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.darkBackground))
                .padding(it)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Box(
                    Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(colorResource(id = R.color.background))
                        .fillMaxWidth()
                        .clickable { changeLoginExpanded = !changeLoginExpanded }
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = "Change Username", color = Color.White)

                        if (changeLoginExpanded) {
                            Spacer(Modifier.height(16.dp))

                            TextField(
                                value = newUsername,
                                onValueChange = { newUsername = it },
                                label = { Text("New username") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                    }
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.DarkGray,
                                    focusedLabelColor = Color.DarkGray,
                                    cursorColor = Color.DarkGray,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedContainerColor = colorResource(R.color.background),
                                    focusedTextColor = Color.Black,
                                    focusedContainerColor = colorResource(R.color.accent),
                                ),
                                modifier = Modifier
                                    .width(260.dp)
                                    .padding(bottom = 16.dp)
                            )

                            Spacer(Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (newUsername.isNotEmpty()) {
                                        coroutineScope.launch {
                                            withContext(Dispatchers.IO) {
                                                val usernameChangeRequest = UsernameChangeRequest(
                                                    database.sessionDao().getOne().token,
                                                    newUsername
                                                )
                                                usernameChangeRequest.request(usernameChangeRequest)
                                            }

                                            myToast(context, "username has been changed")
                                        }
                                    } else {
                                        myToast(context, "username field is empty")
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.accent))
                            ) {
                                Text("Change Username")
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Box(
                    Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(colorResource(id = R.color.background))
                        .fillMaxWidth()
                        .clickable { changePasswordExpanded = !changePasswordExpanded }
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Change Password", color = Color.White)

                        if (changePasswordExpanded) {
                            Spacer(Modifier.height(16.dp))

                            TextField(
                                value = newPassword,
                                onValueChange = { newPassword = it },
                                label = { Text("New password") },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                    }
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.DarkGray,
                                    focusedLabelColor = Color.DarkGray,
                                    cursorColor = Color.DarkGray,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedContainerColor = colorResource(R.color.background),
                                    focusedTextColor = Color.Black,
                                    focusedContainerColor = colorResource(R.color.accent),
                                ),
                                modifier = Modifier
                                    .width(260.dp)
                                    .padding(bottom = 16.dp)
                            )

                            Spacer(Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (newPassword.isNotEmpty()) {
                                        coroutineScope.launch {
                                            withContext(Dispatchers.IO) {
                                                val passwordChangeRequest = PasswordChangeRequest(
                                                    database.sessionDao().getOne().token,
                                                    newPassword
                                                )
                                                passwordChangeRequest.request(passwordChangeRequest)
                                            }

                                            myToast(context, "password has been changed")
                                        }
                                    } else {
                                        myToast(context, "password field is empty")
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.accent))
                            ) {
                                Text("Change Password")
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Box(
                    Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    val signOutRequest = SignOutRequest(database.sessionDao().getOne().token)
                                    database.sessionDao().deleteAll()
                                    signOutRequest.request(signOutRequest)
                                }
                            }
                            navController.navigate("signUp")
                        },
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.background))
                    ) {
                        Text("Log Out", color = Color.White)
                    }
                }
            }
        }
    }
}
