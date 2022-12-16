package com.example.messenger.composable.feed

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodel.ViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Feed(navController: NavController, viewModel: ViewModel) {
    LaunchedEffect(true) {
        while (true) {
            viewModel.getFeed()
            delay(5000)
        }
    }

    val feed by remember { viewModel.feed }
    val users by remember { viewModel.userList }

    Scaffold(topBar = {
        DefaultAppBar("Feed", Icons.Filled.ArrowBack) { navController.popBackStack() }
    },
        content = {
            feedComponent(
                feedList = feed,
                user = users,
                viewModel = viewModel,
                canType = true
            )
        })
}
