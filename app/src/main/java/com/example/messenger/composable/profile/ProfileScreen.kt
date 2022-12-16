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
import com.example.messenger.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profileScreen(navController: NavController, viewModel: ViewModel) {

    val userViewing by remember { viewModel.currentUser }

    val currentUser = getCurrentUser(viewModel.userList.value, userViewing)
    viewModel.getUserPosts(currentUser.id)
    viewModel.addRecent(currentUser)

    fun back(){
        viewModel.removeRecent()
        navController.popBackStack()
    }

    Scaffold(topBar = {
        DefaultAppBar("Profile")  { back() }
    },
        content = { padding ->
            Row(Modifier.background(Color.Black)) {
                profileScreenComponent(navController, viewModel, currentUser)
            }
        })
}





