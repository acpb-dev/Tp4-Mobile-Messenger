package com.example.messenger.composable.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodel.MessengerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profileScreen(navController: NavController, messengerViewModel: MessengerViewModel) {

    val userViewing by remember { messengerViewModel.currentUser }
    var currentUser = getCurrentUser(messengerViewModel.userList.value, userViewing)

    Scaffold(topBar = {
        DefaultAppBar(navController, "Profile")
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                profileScreenComponent(navController, messengerViewModel, currentUser)
            }
        })
}



