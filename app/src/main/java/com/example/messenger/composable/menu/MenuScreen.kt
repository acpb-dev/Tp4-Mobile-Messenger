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
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodels.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, menuViewModel: MenuViewModel) {

    fun unAuthenticate() {
        menuViewModel.sharedViewModel.isAuthenticated.value = false
        menuViewModel.clearSavedCredentials()
        navController.popBackStack()
        navController.navigate(Routes.LoginScreen.route)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        Scaffold(topBar = {
            DefaultAppBar(
                "Messenger App",
                Icons.Filled.Logout
            ) { unAuthenticate() }
        },
            content = {
                MenuComponent(navController, menuViewModel)
            })
    }
}

