package com.example.messenger.composable.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navController: NavController, viewModel: ViewModel) {

    fun unAuthenticate() {
        viewModel.isAuthenticated.value = false
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        Scaffold(topBar = {
            DefaultAppBar(
                "Messenger App",
                Icons.Filled.Logout
            ) { unAuthenticate(); navController.popBackStack() }
        },
            content = {
                menuComponent(navController, viewModel)
            })
    }
}

