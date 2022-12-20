package com.example.messenger

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.messenger.api.SocialNetworkApiImpl
import com.example.messenger.composable.feed.FeedScreen
import com.example.messenger.composable.feed.UserFeedScreen
import com.example.messenger.composable.friendList.FriendsScreen
import com.example.messenger.composable.login.LoginScreen
import com.example.messenger.composable.menu.MenuScreen
import com.example.messenger.composable.profile.profileScreen
import com.example.messenger.composable.profile.UpdateProfileScreen
import com.example.messenger.composable.register.SignUp
import com.example.messenger.composable.searchUsers.SearchScreen
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodels.*

class MainActivity : ComponentActivity() {
    private var sharedPreferences: SharedPreferences? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("login_information", MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        val socialNetworkApi = SocialNetworkApiImpl(sharedPreferences!!)
        setContent {
            MainScreen(socialNetworkApi)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainScreen(socialNetworkApi: SocialNetworkApiImpl) {
    val navController: NavHostController = rememberNavController()
    val sharedViewModel = SharedViewModel(socialNetworkApi)
    NavHost(navController = navController, startDestination = Routes.LoginScreen.route) {

        composable(route = Routes.LoginScreen.route) {
            val loginViewModel = LoginViewModel(socialNetworkApi, sharedViewModel)
            LoginScreen(navController, loginViewModel)
        }

        composable(route = Routes.RegisterScreen.route) {
            val registerViewModel = RegisterViewModel(socialNetworkApi, sharedViewModel)
            SignUp(navController, registerViewModel)
        }

        composable(route = Routes.MenuScreen.route) {
            val menuViewModel = MenuViewModel(socialNetworkApi, sharedViewModel)
            sharedViewModel.getAllUsers(true)
            MenuScreen(navController, menuViewModel)
        }

        composable(route = Routes.FeedScreen.route) {
            val feedViewModel = FeedViewModel(socialNetworkApi, sharedViewModel)
            FeedScreen(navController, feedViewModel)
        }

        composable(route = Routes.UserFeedScreen.route) {
            val feedViewModel = FeedViewModel(socialNetworkApi, sharedViewModel)
            UserFeedScreen(navController = navController, feedViewModel = feedViewModel)
        }

        composable(route = Routes.FriendListScreen.route) {
            sharedViewModel.clearRecent()
            val friendListViewModel = FriendListViewModel(socialNetworkApi, sharedViewModel)
            FriendsScreen(navController = navController, friendListViewModel = friendListViewModel)
        }

        composable(route = Routes.ProfileScreen.route) {
            val profileViewModel = ProfileViewModel(socialNetworkApi, sharedViewModel)
            profileScreen(navController = navController, profileViewModel = profileViewModel)
        }

        composable(route = Routes.SearchFriendScreen.route) {
            val searchViewModel = SearchViewModel(socialNetworkApi, sharedViewModel)
            SearchScreen(navController = navController, searchViewModel = searchViewModel)
        }

        composable(route = Routes.UpdateProfileScreen.route) {
            val updateProfileViewModel = UpdateProfileViewModel(socialNetworkApi, sharedViewModel)
            UpdateProfileScreen(navController = navController, updateProfileViewModel = updateProfileViewModel)
        }
    }
}

