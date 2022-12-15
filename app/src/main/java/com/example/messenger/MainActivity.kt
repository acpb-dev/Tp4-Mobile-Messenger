package com.example.messenger

import android.content.SharedPreferences
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
import com.example.messenger.composable.menu.Menu
import com.example.messenger.data.api.SocialNetworkApiImpl
import com.example.messenger.composable.feed.feed
import com.example.messenger.composable.friendList.myFriends
import com.example.messenger.composable.login.LoginScreen
import com.example.messenger.composable.profile.profileScreen
import com.example.messenger.composable.searchUsers.search
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel

class MainActivity : ComponentActivity() {
    var sharedPreferences: SharedPreferences? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("login_information", MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        val socialNetworkApi = SocialNetworkApiImpl(sharedPreferences!!)
        val messengerViewModel = MessengerViewModel(socialNetworkApi);
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
        composable(route = Routes.LoginScreen.route){
            LoginScreen(navController, messengerViewModel)
        }

        composable(route = Routes.Menu.route){
            messengerViewModel.getAllUsers();
            messengerViewModel.getFriendsList()
            Menu(navController, messengerViewModel)
        }

        composable(route = Routes.Feed.route){
            feed(navController, messengerViewModel)
        }

        composable(route = Routes.FriendList.route ) {
            myFriends(navController = navController, messengerViewModel = messengerViewModel)
        }

        composable(route = Routes.MyProfile.route){
            profileScreen(navController = navController, messengerViewModel = messengerViewModel)
        }

        composable(route = Routes.SearchFriend.route ){
            search(navController = navController, messengerViewModel = messengerViewModel)
        }

    }
}

