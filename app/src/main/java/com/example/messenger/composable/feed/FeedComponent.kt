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
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.api.data.FeedItem
import com.example.messenger.api.data.FeedList
import com.example.messenger.api.data.PostInfo
import com.example.messenger.api.data.Users
import com.example.messenger.viewmodel.ViewModel
import java.time.LocalDateTime

@Composable
fun FeedComponent(feedList: FeedList, user: Users, viewModel: ViewModel, canType: Boolean) {
//    val reverse: List<FeedItem> = feedList.reversed()
    Box(
        Modifier
            .background(Black),
        contentAlignment = Alignment.Center
    ) {
        if (feedList.isEmpty()) {
            Column(
                Modifier
                    .background(Black)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No Posts to display", color = White, textAlign = TextAlign.Center)
            }
        }

        if (feedList.isNotEmpty()) {
            LazyColumn(
                Modifier
                    .background(Black)
                    .fillMaxSize(),
                reverseLayout = true
            ) {

                itemsIndexed(feedList) { index, message ->
                    if (index == 0 && canType) {
                        MessageInput(viewModel)
                    }
                    MessageCard1(message, user)
                }
            }
        } else {
            Column(
                Modifier
                    .background(Black)
                    .fillMaxSize(),

            ) {
                if (canType) {
                    Spacer(modifier = Modifier.weight(1f))
                    MessageInput(viewModel)
                }
            }
        }
    }
}

fun getName(uid: String, users: Users): String {
    users.forEach {
        if (uid == it.id) {
            return it.firstname + " " + it.lastname
        }
    }
    return "Deleted User"
}

@Composable
fun MessageCard1(feedPost: FeedItem, user: Users) {
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
                else -> DarkGray
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = feedPost.text,
                color = when {
                    feedPost.isFromCurrentUser -> MaterialTheme.colors.onPrimary
                    else -> White
                },
            )
        }
        feedPost.images.forEach {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
        }
        Text(
            // 4
            text = when {
                feedPost.isFromCurrentUser -> "Me"
                else -> getName(feedPost.user, user)
            },
            color = White,
            fontSize = 12.sp,
        )
        Text(
            // 4
            text = formatTime(localDateTime),
            color = White,
            fontSize = 8.sp,
        )

    }
}

fun formatTime(time: LocalDateTime): String {
    return time.hour.toString() + ":" + time.minute.toString().padStart(
        2,
        '0'
    ) + " " + time.dayOfMonth.toString() + " " + time.month.toString() + " " + time.year
}

@Composable
fun cardShapeFor1(messageItem: FeedItem): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        messageItem.isFromCurrentUser -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

@Composable
fun MessageInput(viewModel: ViewModel) {
    var inputValue by remember { mutableStateOf("") } // 2

    fun sendMessage() {
        inputValue = inputValue.trim()
        if (inputValue != "") {
            val body = splitImages(inputValue)
            viewModel.postFeed(body)
            inputValue = ""
        }
    }

    Row {
        TextField(
            // 4
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
        Button(
            // 5
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

fun splitImages(input: String): PostInfo {
    val split = input.split('[')
    if (split.size > 1) {
        val images = split[1].trimEnd(']').split(',')
        return PostInfo(text = split[0], images = images)
    }
    return PostInfo(text = input)
}



