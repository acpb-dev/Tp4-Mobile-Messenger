package com.example.messenger.individualConversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.messenger.viewmodel.data.ConvoListData
import com.example.messenger.viewmodel.data.Messages

@Composable
fun messagesComponent(individualConvo: Messages, user: ConvoListData) {
    MessageCard(individualConvo, user)
}

@Composable
fun MessageCard(messageItem: Messages, user: ConvoListData) { // 1
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when { // 2
            messageItem.isMine -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp),
            shape = cardShapeFor(messageItem), // 3
            backgroundColor = when {
                messageItem.isMine -> MaterialTheme.colors.primary
                else -> MaterialTheme.colors.secondary
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = messageItem.messages,
                color = when {
                    messageItem.isMine -> MaterialTheme.colors.onPrimary
                    else -> MaterialTheme.colors.onSecondary
                },
            )
        }
        Text( // 4
            text = when {
                messageItem.isMine -> "Me"
                else -> user.user.firstName},
            color = White,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun cardShapeFor(messageItem: Messages): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        messageItem.isMine -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}



