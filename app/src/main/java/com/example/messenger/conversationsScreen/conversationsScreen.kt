package com.example.messenger.conversationsScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.messenger.utils.const.Routes
import com.example.messenger.viewmodel.MessengerViewModel
import com.example.messenger.viewmodel.SearchWidget
import com.example.messenger.viewmodel.data.ConvoListData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationsScreen(navController: NavController, messengerViewModel: MessengerViewModel) {
    val searchWidgetState by messengerViewModel.searchWidgetState
    val searchTextState by messengerViewModel.searchTextState

    val convosList = remember { mutableStateOf(messengerViewModel.convoList) }

    val navigateToConvoSelected: (convoListData: ConvoListData) -> Unit = {
            individualConvo ->
//        messengerViewModel.getConversation(individualConvo.user.uid);
        navController.navigate(Routes.ConversationIndividual.route)
    }

    Scaffold(topBar = {
        MainAppBar(
            searchWidgetState = searchWidgetState,
            searchTextState = searchTextState,
            onTextChange = {
                messengerViewModel.updateSearchTextState(newValue = it)

            },
            onCloseClicked = {
                messengerViewModel.updateSearchWidgetState(newValue = SearchWidget.SearchWidgetState.CLOSED)
            },
            onSearchClicked = {
                Log.d("Searched Text", it)
            },
            onSearchTriggered = {
                messengerViewModel.updateSearchWidgetState(newValue = SearchWidget.SearchWidgetState.OPENED)
            }
        )
    },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                ConversationListComponent(
                    convoList = convosList.value,
                    itemOnClick = navigateToConvoSelected
                )
            }
        })
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