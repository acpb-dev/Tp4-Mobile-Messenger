package com.example.messenger.composable.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.data.PostInfo
import com.example.messenger.viewmodel.data.Users
import com.example.messenger.viewmodel.data.feedItem
import com.example.messenger.viewmodel.data.feedList
import java.time.LocalDateTime

@Composable
fun feedComponent(feedList: feedList, user: Users, messengerViewModel: MessengerViewModel) {
    val reverse: List<feedItem> = feedList.reversed();
    Box(
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            reverseLayout = true
        ) {
            itemsIndexed(feedList) { index, message ->
                if(index == 0){
                    MessageInput(messengerViewModel)
                }
                MessageCard1(message, user)
            }
        }
    }
}

fun getName(uid: String, users: Users): String{
    users.forEach{
        if (uid == it.id){
            return it.firstname + " " + it.lastname
        }
    }
    return "Deleted User"
}

@Composable
fun MessageCard1(feedPost: feedItem, user: Users) {
    val localDateTime = LocalDateTime.parse(feedPost.createdAt)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when { // 2
            feedPost.isFromCurrentUser -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp),
            shape = cardShapeFor1(feedPost), // 3
            backgroundColor = when {
                feedPost.isFromCurrentUser -> MaterialTheme.colors.primary
                else -> Color.DarkGray
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = feedPost.text,
                color = when {
                    feedPost.isFromCurrentUser -> MaterialTheme.colors.onPrimary
                    else -> Color.White
                },
            )
        }
        Text( // 4
            text = when {
                feedPost.isFromCurrentUser -> "Me"
                else -> getName(feedPost.user, user)
            },
            color = Color.White,
            fontSize = 12.sp,
        )
        Text( // 4
            text = formatTime(localDateTime),
            color = Color.White,
            fontSize = 8.sp,
        )
        feedPost.images.forEach{
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
        }
    }
}

fun formatTime(time: LocalDateTime): String{
    return time.hour.toString() + ":" + time.minute.toString().padStart(2, '0') + " " + time.dayOfMonth.toString() + " " + time.month.toString() + " " + time.year
}

@Composable
fun cardShapeFor1(messageItem: feedItem): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        messageItem.isFromCurrentUser -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

@Composable
fun MessageInput(messengerViewModel: MessengerViewModel) {
    var inputValue by remember { mutableStateOf("") } // 2

    fun sendMessage() {
        if (inputValue.trim() != ""){
            val body = PostInfo(text = inputValue)
            messengerViewModel.postFeed(body);
            inputValue = ""
        }
    }

    Row {
        TextField( // 4
            modifier = Modifier.weight(1f),
            value = inputValue,
            colors = TextFieldDefaults.textFieldColors(
                textColor = White,
                disabledTextColor = DarkGray,
                backgroundColor = DarkGray,
                cursorColor = DarkGray,
                errorCursorColor = DarkGray,
                focusedIndicatorColor = DarkGray
            ),
            onValueChange = { inputValue = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions { sendMessage() },
        )
        Button( // 5
            modifier = Modifier
                .height(56.dp)
                .background(DarkGray)
                .clip(RoundedCornerShape(100.dp)),
            onClick = { sendMessage() },
            enabled = inputValue.isNotBlank(),
        ) {
            Icon( // 6
                imageVector = Icons.Outlined.Send,
                contentDescription = ""
            )
        }
    }
}



