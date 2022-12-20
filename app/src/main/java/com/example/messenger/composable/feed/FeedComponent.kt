package com.example.messenger.composable.feed

import android.widget.Toast
import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messenger.api.data.FeedItem
import com.example.messenger.api.data.FeedList
import com.example.messenger.api.data.PostInfo
import com.example.messenger.api.data.Users
import com.example.messenger.utils.navToProfile
import com.example.messenger.viewmodels.FeedViewModel
import java.time.LocalDateTime

@Composable
fun FeedComponent(navController: NavController, feedList: FeedList, user: Users, feedViewModel: FeedViewModel, canType: Boolean) {
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
                        MessageInput(feedViewModel)
                    }
                    MessageCard1(navController, feedViewModel, message, user)
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
                    MessageInput(feedViewModel)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageCard1(navController: NavController, feedViewModel: FeedViewModel, feedPost: FeedItem, user: Users) {
    val context = LocalContext.current
    val localDateTime = LocalDateTime.parse(feedPost.createdAt)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when {
            feedPost.isFromCurrentUser -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp).combinedClickable(
                onClick = { },
                onLongClick = {
                    val result = likePost(feedViewModel, feedPost)
                    if (result != ""){
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                    }
                }),
            shape = cardShapeFor1(feedPost),
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
        Row(Modifier.padding(top = 10.dp)) {
            Text(text = "Likes: ", color = Red)
            Text(text = feedPost.likes.count().toString(), color = White)

        }

        feedPost.images.forEach {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
        }
        Text(
            text = when {
                feedPost.isFromCurrentUser -> "Me"
                else -> getName(feedPost.user, user)
            },
            color = White,
            fontSize = 12.sp,
        )
        Row(Modifier.clickable(onClick = { if (!feedPost.isFromCurrentUser){ navToProfile(navController, feedViewModel.sharedViewModel, feedViewModel.getUserById(feedPost.user)) } })) {
            Text(
                text = formatTime(localDateTime),
                color = White,
                fontSize = 8.sp,
            )
        }
    }
}


fun likePost(feedViewModel: FeedViewModel, feedPost: FeedItem): String{
    val myId = feedViewModel.sharedViewModel.myUser.value.id
    if(!feedPost.isFromCurrentUser){
        feedViewModel.likePost(feedPost.id)
        return if (feedPost.likes.contains(myId)){
            "UnLiked Post"

        }else{
            "Liked Post"
        }
    }
    return ""
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
fun MessageInput(feedViewModel: FeedViewModel) {
    var inputValue by remember { mutableStateOf("") }

    fun sendMessage() {
        inputValue = inputValue.trim()
        if (inputValue != "") {
            val body = splitImages(inputValue)
            feedViewModel.postFeed(body)
            inputValue = ""
        }
    }

    Row {
        TextField(
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
            modifier = Modifier
                .height(56.dp)
                .background(DarkGray)
                .clip(RoundedCornerShape(100.dp)),
            onClick = { sendMessage() },
            enabled = inputValue.isNotBlank(),
        ) {
            Icon(
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



