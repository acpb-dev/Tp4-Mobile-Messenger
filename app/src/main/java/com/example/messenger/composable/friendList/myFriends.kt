package com.example.messenger.composable.friendList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.data.UsersItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myFriends(navController: NavController, messengerViewModel: MessengerViewModel) {

    val friends by remember { messengerViewModel.myFriends }
//    val search by remember {messengerViewModel.searchedUser}

    Scaffold(topBar = {
        DefaultAppBar(navController)
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                myFriendsComponent(navController = navController, messengerViewModel = messengerViewModel, friends)
            }
        })




}

@Composable
fun DefaultAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.clickable(onClick = { navController.popBackStack() })) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        backgroundColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
        actions = {
            Column(Modifier.padding(5.dp)) {
                Text(text = "Friends List", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
        }
    )
}


