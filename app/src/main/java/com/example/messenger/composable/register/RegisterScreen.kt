package com.example.messenger.composable.register


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.messenger.composable.DefaultAppBar
import com.example.messenger.viewmodels.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController, registerViewModel: RegisterViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        androidx.compose.material3.Scaffold(topBar = {
            DefaultAppBar(
                "Register",
                Icons.Filled.ArrowBack
            ) { navController.popBackStack() }
        },
            content = {
                RegisterComponent(navController, registerViewModel)
            })
    }
}