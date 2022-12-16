package com.example.messenger.composable.register

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.ViewModel

@Composable
fun RegisterComponent(navController: NavController, viewModel: ViewModel) {
    val isAuthenticated by remember { viewModel.isAuthenticated }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            viewModel.usernameStored.value = email
            navController.navigate(Routes.Menu.route)
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
            TextField(
                value = firstname,
                modifier = Modifier.padding(15.dp),
                onValueChange = { firstname = it },
                label = { Text("Firstname") },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = Color.White
                )
            )

            TextField(
                value = lastname,
                modifier = Modifier.padding(15.dp),
                onValueChange = { lastname = it },
                label = { Text("Lastname") },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = Color.White
                )
            )

            TextField(
                value = email,
                modifier = Modifier.padding(15.dp),
                onValueChange = { email = it },
                label = { Text("email") },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = Color.White
                )
            )

            TextField(
                value = password,
                modifier = Modifier.padding(15.dp),
                onValueChange = { password = it },
                label = { Text("Password") },
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = Color.White
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

            TextField(
                value = password2,
                modifier = Modifier.padding(15.dp),
                onValueChange = { password2 = it },
                label = { Text("Password") },
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = Color.White
                )
            )

            Button(
                modifier = Modifier.padding(15.dp),
                onClick = {
                    val error = verifyInfo(email = email, password = password, password2 = password2, fName = firstname, lName = lastname)

                    if (error == ""){
                        viewModel.signUp(
                            email = email,
                            password = password,
                            firstname = firstname,
                            lastname = lastname
                        )
                    }else{
                        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
            )

            {
                Text(text = "Register", color = Color.White)
            }
        }
    }
}

fun verifyInfo(email: String, password: String, password2: String, fName: String, lName: String): String{
    val emailT = email.trim();
    val passwordT = password.trim();
    val fNameT = fName.trim();
    val lNameT = lName.trim();
    var error = ""
    if (fNameT == ""){
        error = "Firstname cannot be empty "
    }
    if (lNameT == ""){
        error = "Lastname cannot be empty "
    }
    if (passwordT == ""){
        error = "Password cannot be empty"
    }

    if (password2 != password){
        error = "Passwords must match"
    }
    if (!emailT.contains('@')){
        error = "Invalid Email address"
    }
    return error;
}