package com.example.messenger

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.messenger.conversationsScreen.ConversationsScreen
import com.example.messenger.individualConversation.individualConversation
import com.example.messenger.presentation.login.LoginScreen
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val messengerViewModel = MessengerViewModel();
        setContent {
            MainScreen(messengerViewModel);
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainScreen(messengerViewModel: MessengerViewModel) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LoginScreen.route){
        composable(
            route = Routes.LoginScreen.route
        ){
            LoginScreen(navController, messengerViewModel)
        }

        composable(
            route = Routes.ConversationsScreen.route
        ){
            ConversationsScreen(navController, messengerViewModel)
        }

        composable(
            route = Routes.ConversationIndividual.route
        ){
            individualConversation(navController, messengerViewModel)
        }

    }
}

