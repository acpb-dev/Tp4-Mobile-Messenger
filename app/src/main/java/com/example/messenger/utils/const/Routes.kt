package com.example.messenger.utils.const

sealed class Routes(val route: String) {
    object LoginScreen: Routes("login")
    object ConversationsScreen: Routes("conversations")
    object ConversationIndividual: Routes("individualConvo")
}
