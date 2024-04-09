import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.rosk3r.composetest.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF66756E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 68.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 68.dp)
            )

            Text(
                text = "Create an account",
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.DarkGray,
                    focusedLabelColor = Color.DarkGray,
                    cursorColor = Color.DarkGray,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color(0xFF73837B),
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFF97aba1),
                ),
                modifier = Modifier
                    .width(260.dp)
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.DarkGray,
                    focusedLabelColor = Color.DarkGray,
                    cursorColor = Color.DarkGray,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color(0xFF73837B),
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFF97aba1),
                ),
                modifier = Modifier
                    .width(260.dp)
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
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
                    unfocusedContainerColor = Color(0xFF73837B),
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFF97aba1),
                ),
                modifier = Modifier
                    .width(260.dp)
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    // Perform signup logic here
                    // For demonstration purposes, let's navigate to another screen
                    navController.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF97aba1)
                )
            ) {
                Text(text = "Sign Up")
            }


        }
        Text(
            text = "login in existed account",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .clickable {
                    navController.navigate("signIn") {
                        launchSingleTop = true
                        popUpTo("signUp") { inclusive = true }
                        anim {
                            slideInHorizontally(initialOffsetX = { 300 }) + fadeIn() to slideOutHorizontally(
                                targetOffsetX = { -300 }) + fadeOut()
                        }
                    }
                }
        )
    }
}
