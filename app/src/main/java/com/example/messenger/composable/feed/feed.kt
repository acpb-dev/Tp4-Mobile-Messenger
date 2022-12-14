package com.example.messenger.composable.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.messenger.viewmodel.MessengerViewModel
import kotlinx.coroutines.delay
import androidx.compose.material.Icon as MaterialIcon
import androidx.compose.material3.Text as Material3Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun feed(navController: NavController, messengerViewModel: MessengerViewModel) {
    LaunchedEffect(true){
        while (true){
            messengerViewModel.feed()
            delay(5000)
        }
    }

    val feed by remember { messengerViewModel.feed }
    val users by remember { messengerViewModel.userList }

    Scaffold(topBar = {
        DefaultAppBar(navController)
    },
        content = { padding ->
            Row(Modifier.background(Black)) {
                feedComponent(
                    feedList = feed,
                    user = users,
                    messengerViewModel = messengerViewModel
                )
            }
        })

}

@Composable
fun DefaultAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.clickable(onClick = { navController.popBackStack() })) {
                MaterialIcon(Icons.Filled.ArrowBack, "")
            }
        },
        backgroundColor = Color.DarkGray,
        actions = {
            Column(Modifier.padding(5.dp)) {
                Material3Text(text = "Feed", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
        }
    )
}




