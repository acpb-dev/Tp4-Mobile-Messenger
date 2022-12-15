package com.example.messenger.composable.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodel.MessengerViewModel
import kotlinx.coroutines.delay

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
        DefaultAppBar(navController, "Feed")
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





