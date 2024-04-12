package com.example.aiqrcode

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Box(contentAlignment = Alignment.Center) {
        if (uiState.loading) {
            CircularProgressIndicator()
        } else if (uiState.bitmap != null) {
            Image(
                bitmap = uiState.bitmap!!.asImageBitmap(),
                contentDescription = "",
            )
        } else {
            Button(
                modifier = Modifier.size(200.dp, 50.dp),
                onClick = { viewModel.sendRequest() }) {
                Text("Generate")
            }
        }
    }
}