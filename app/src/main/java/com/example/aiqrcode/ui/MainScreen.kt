package com.example.aiqrcode.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    SingleEventEffect(viewModel.sideEffects) { sideEffect ->
        when (sideEffect) {
            is SideEffect.ShowMessage -> Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
        }
    }

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

@Composable
fun <T : Any> SingleEventEffect(
    sideEffects: Flow<T>,
    lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffects) {
        lifecycleOwner.repeatOnLifecycle(lifeCycleState) {
            sideEffects.collect(collector)
        }
    }
}