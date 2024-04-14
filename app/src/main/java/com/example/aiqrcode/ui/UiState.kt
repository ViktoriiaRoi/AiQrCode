package com.example.aiqrcode.ui

import android.graphics.Bitmap

data class UiState(
    val setupParams: SetupParams = SetupParams(),
    val bitmap: Bitmap? = null
)

data class SetupParams(
    val prompt: String = ""
)