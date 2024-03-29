package com.example.messenger.composable.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    messengerViewModel: LoginViewModel
) {
    var email by remember { messengerViewModel.email }
    var password by remember {messengerViewModel.password }
    var passwordVisible by remember { messengerViewModel.passwordVisible }
    val isAuthenticated by remember { messengerViewModel.sharedViewModel.isAuthenticated }


    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            navController.navigate(Routes.MenuScreen.route)
        }
    }
    Column(
        Modifier
            .background(color = Color.Black)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Messenger",
                fontSize = 30.sp,
                color = White,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(6.dp)
            )


            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("email") },
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = White,
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = White
                )
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = White,
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = White
                ),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Button(
                onClick = {
                        messengerViewModel.signIn(email, password)

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
            )

            {
                Text(text = "Login", color = White)
            }
            Button(
                onClick = {
                        navController.navigate(Routes.RegisterScreen.route)

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGray)
            )

            {
                Text(text = "Register", color = White)
            }
        }
    }

}

