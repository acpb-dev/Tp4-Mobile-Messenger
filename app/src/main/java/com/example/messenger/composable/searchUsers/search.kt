package com.example.messenger.composable.searchUsers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.messenger.viewmodel.MessengerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(navController: NavController, messengerViewModel: MessengerViewModel) {
    val usersFound by remember { messengerViewModel.searchedUser }
    var search by remember { mutableStateOf("") }

    Scaffold(topBar = {
        DefaultAppBar1(navController)
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                searchComponent(navController = navController, messengerViewModel = messengerViewModel, usersFound)
            }
        })
}

fun searchString(searchString: String, messengerViewModel: MessengerViewModel){
    if (searchString.trim() != ""){
        messengerViewModel.getUserByName(searchString)
    }
}


@Composable
fun DefaultAppBar1(navController: NavController) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.clickable(onClick = { navController.popBackStack() })) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
            Column(Modifier.padding(5.dp)) {
                Text(text = "Friends List", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
        }
    )
}