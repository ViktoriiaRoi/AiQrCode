package com.example.aiqrcode.ui.screens

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    bitmap: Bitmap?,
    onBack: () -> Unit = {},
    onSave: (Bitmap) -> Unit = {}
) {
    if (bitmap == null) {
        Loading()
    } else {
        ImageResult(bitmap, onBack, onSave)
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ImageResult(
    bitmap: Bitmap,
    onBack: () -> Unit = {},
    onSave: (Bitmap) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ResultIconButton(icon = Icons.AutoMirrored.Default.ArrowBack, onClick = onBack)
            ResultIconButton(icon = Icons.Default.Download, onClick = { onSave(bitmap) })
        }
    }
}

@Composable
private fun ResultIconButton(icon: ImageVector, onClick: () -> Unit = {}) {
    FilledIconButton(onClick = onClick, modifier = Modifier.size(48.dp)) {
        Icon(
            icon,
            contentDescription = "",
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ResultScreenPreview() {
    val bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888).apply {
        eraseColor(Color.BLUE)
    }
    ResultScreen(bitmap = bitmap)
}
