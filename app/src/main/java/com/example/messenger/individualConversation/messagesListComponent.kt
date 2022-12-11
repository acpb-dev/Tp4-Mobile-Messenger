package com.example.messenger.individualConversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.data.ConvoListData
import com.example.messenger.viewmodel.data.IndividualConvo

@Composable
fun messagesListComponent(convo: MutableState<IndividualConvo>, user: ConvoListData) {
    val convoList = convo.component1().messages
    LazyColumn(
        Modifier
            .fillMaxSize().background(Color.Black)){
        itemsIndexed(convoList) { index, convo ->
            messagesComponent(convo, user)
        }
    }
}

