package com.example.aiqrcode.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SetupScreen(
    sendRequest: () -> Unit = {}
) {
    Box(contentAlignment = Alignment.Center) {
        Button(
            modifier = Modifier.size(200.dp, 50.dp),
            onClick = { sendRequest() }) {
            Text("Generate")
        }
    }
}