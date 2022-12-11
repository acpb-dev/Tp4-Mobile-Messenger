package com.example.messenger.individualConversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.SearchWidget
import com.example.messenger.viewmodel.data.ConvoListData
import com.example.messenger.viewmodel.data.Messages
import androidx.compose.material3.Button as Button1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun individualConversation(navController: NavController, messengerViewModel: MessengerViewModel, user: ConvoListData) {
    val searchWidgetState by messengerViewModel.searchWidgetState
    val searchTextState by messengerViewModel.searchTextState
    messengerViewModel.getMessages();

    val convo = remember { mutableStateOf(messengerViewModel.convosMessages) }





    Column(Modifier.fillMaxSize().background(Black)) {

        Row {
            MessageList(
                messages = convo.value,
                user = user,
                messengerViewModel = messengerViewModel
            )
        }
    }
}








@Composable
fun MessageList(messages: MutableList<Messages>, user: ConvoListData, messengerViewModel: MessengerViewModel) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            reverseLayout = true
        ) {
            itemsIndexed(messages) { index, message ->
                if(index == 0){
                    MessageInput(messengerViewModel, user)
                }
                MessageCard1(message, user)

            }


        }

    }
}



@Composable
fun MessageCard1(messageItem: Messages, user: ConvoListData) {
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
            shape = cardShapeFor1(messageItem), // 3
            backgroundColor = when {
                messageItem.isMine -> MaterialTheme.colors.primary
                else -> DarkGray
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = messageItem.messages,
                color = when {
                    messageItem.isMine -> MaterialTheme.colors.onPrimary
                    else -> White
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
fun cardShapeFor1(messageItem: Messages): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        messageItem.isMine -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

@Composable
fun MessageInput(messengerViewModel: MessengerViewModel, user: ConvoListData) {
    var inputValue by remember { mutableStateOf("") } // 2

    fun sendMessage() { // 3
        messengerViewModel.pushMessage(inputValue);
        inputValue = ""

    }

    Row {
        TextField( // 4
            modifier = Modifier.weight(1f),
            value = inputValue,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = DarkGray,
                focusedIndicatorColor =  Color.Transparent, //hide the indicator
                unfocusedIndicatorColor = DarkGray),
            onValueChange = { inputValue = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions { sendMessage() },
        )
        Button1( // 5
            modifier = Modifier.height(56.dp).background(MaterialTheme.colors.primary),
            onClick = { sendMessage() },
            enabled = inputValue.isNotBlank(),
        ) {
            Icon( // 6
                imageVector = Icons.Default.Send,
                contentDescription = ""
            )
        }
    }
}















@Composable
fun MainAppBar(
    searchWidgetState: SearchWidget.SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidget.SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidget.SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Messenger", color = Color.White
            )
        },
        backgroundColor = Color.DarkGray,
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.DarkGray
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.LightGray
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                textColor = Color.White,
            ))
    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}