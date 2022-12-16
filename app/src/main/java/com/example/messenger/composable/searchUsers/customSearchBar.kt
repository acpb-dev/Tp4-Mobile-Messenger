package com.example.messenger.composable.searchUsers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.messenger.viewmodel.ViewModel
import com.example.messenger.utils.SearchWidget
import com.example.messenger.api.data.Users

@Composable
fun searchTopBar(viewModel: ViewModel, navController: NavController){
    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState
    MainAppBar(
        searchWidgetState = searchWidgetState,
        searchTextState = searchTextState,
        onTextChange = {
            viewModel.updateSearchTextState(newValue = it)

        },
        onCloseClicked = {
            viewModel.updateSearchWidgetState(newValue = SearchWidget.SearchWidgetState.CLOSED)
        },
        onSearchClicked = {
            Log.d("Searched Text", it)
        },
        onSearchTriggered = {
            viewModel.updateSearchWidgetState(newValue = SearchWidget.SearchWidgetState.OPENED)
        },
        navController = navController
    )
}

@Composable
fun searchResults(navController: NavController, viewModel: ViewModel, usersFound: Users){
    Row(Modifier.background(Color.Black)) {
        searchComponent(navController = navController, viewModel = viewModel, usersFound)
    }
}



@Composable
fun MainAppBar(
    searchWidgetState: SearchWidget.SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    navController: NavController
) {
    when (searchWidgetState) {
        SearchWidget.SearchWidgetState.CLOSED -> {
            AppBar(
                onSearchClicked = onSearchTriggered,
                navController = navController
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
fun AppBar(onSearchClicked: () -> Unit, navController: NavController) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.clickable(onClick = { navController.popBackStack() }).padding(end = 20.dp)) {
                androidx.compose.material.Icon(Icons.Filled.ArrowBack, "", tint = Color.White)
            }


        },
        backgroundColor = Color.DarkGray,
        actions = {
            Text(text = "Search", fontSize = 25.sp, color = Color.White, fontWeight = FontWeight.Bold)
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
