package com.example.messenger.utils.const

sealed class Routes(val route: String) {
    object LoginScreen : Routes("login")
    object RegisterScreen : Routes("register")
    object MenuScreen : Routes("menu")
    object FeedScreen : Routes("feed")
    object UserFeedScreen : Routes("userFeed")
    object FriendListScreen : Routes("friendList")
    object ProfileScreen : Routes("myProfile")
    object SearchFriendScreen : Routes("searchFriends")
    object UpdateProfileScreen : Routes("updateProfile")
}
