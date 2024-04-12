package com.example.aiqrcode

import android.graphics.Bitmap

data class UiState(
    val loading: Boolean = false,
    val bitmap: Bitmap? = null
)
