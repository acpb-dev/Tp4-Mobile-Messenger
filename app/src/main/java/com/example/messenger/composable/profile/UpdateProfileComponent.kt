package com.example.messenger.composable.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.messenger.viewmodels.UpdateProfileViewModel

@Composable
fun UpdateProfileComponent(navController: NavController, updateProfileViewModel: UpdateProfileViewModel) {
    val currentUser by remember { mutableStateOf(updateProfileViewModel.sharedViewModel.currentUser) }
    var imgLink by remember { mutableStateOf(currentUser.value.profileImgUrl) }
    var firstName by remember { mutableStateOf(currentUser.value.firstname) }
    var lastName by remember { mutableStateOf(currentUser.value.lastname) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Update Image Link:", color = White, textAlign = TextAlign.Center)
            TextField(
                shape = RoundedCornerShape(8.dp),
                value = imgLink,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = White,
                    disabledTextColor = DarkGray,
                    backgroundColor = DarkGray,
                    cursorColor = DarkGray,
                    errorCursorColor = DarkGray,
                    focusedIndicatorColor = DarkGray
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                onValueChange = { imgLink = it }
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Update First Name:", color = White, textAlign = TextAlign.Center)
            TextField( // 4,
                shape = RoundedCornerShape(8.dp),
                value = firstName,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = White,
                    disabledTextColor = DarkGray,
                    backgroundColor = DarkGray,
                    cursorColor = DarkGray,
                    errorCursorColor = DarkGray,
                    focusedIndicatorColor = DarkGray
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                onValueChange = { firstName = it }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Update Last Name:", color = White, textAlign = TextAlign.Center)
            TextField( // 4
                shape = RoundedCornerShape(8.dp),
                value = lastName,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = White,
                    disabledTextColor = DarkGray,
                    backgroundColor = DarkGray,
                    cursorColor = DarkGray,
                    errorCursorColor = DarkGray,
                    focusedIndicatorColor = DarkGray
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                onValueChange = { lastName = it }
            )
        }

        Button(
            onClick = {
                updateProfileViewModel.updateProfile(firstName, lastName, imgLink); navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
        )

        {
            androidx.compose.material3.Text(text = "UPDATE", color = White)
        }
    }
}