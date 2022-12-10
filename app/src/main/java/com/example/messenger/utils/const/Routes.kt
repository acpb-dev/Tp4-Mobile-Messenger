package com.example.messenger.utils.const

sealed class Routes(val route: String) {
    object ContactList: Routes("contact_list")
    object Contact: Routes("contact")
}
