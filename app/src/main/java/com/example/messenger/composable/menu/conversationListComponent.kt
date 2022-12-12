package com.example.messenger.composable.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.messenger.viewmodel.data.ConvoListData

@Composable
fun ConversationListComponent(convoList: Map<String, ConvoListData>, itemOnClick: (convo: ConvoListData) -> Unit) {
    val convoList = convoList.values.toList()
    LazyColumn(
        Modifier
            .fillMaxSize().background(Color.Black)){
        itemsIndexed(convoList) { index, convo ->
            ConversationComponent(convo) { itemOnClick(convo) }
            if(index < convoList.lastIndex){
                Divider(color = Color.White, thickness = 1.dp, startIndent = 60.dp)
            }
        }
    }
}