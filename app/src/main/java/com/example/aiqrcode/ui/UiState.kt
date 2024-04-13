package com.example.aiqrcode.ui

import android.graphics.Bitmap

data class UiState(
    val loading: Boolean = false,
    val bitmap: Bitmap? = null
)
