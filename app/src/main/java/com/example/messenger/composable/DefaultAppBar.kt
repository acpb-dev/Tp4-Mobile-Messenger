package com.example.messenger.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DefaultAppBar(tabName: String, icon: ImageVector, onClick: () -> Unit) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.clickable(onClick = { onClick() })) {
                Icon(icon, "", tint = Color.White)
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
            Column(Modifier.padding(10.dp)) {
                Text(
                    text = tabName,
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}
