package com.example.aiqrcode.ui

import android.graphics.Bitmap

data class UiState(
    val setupParams: SetupParams = SetupParams(),
    val setupErrors: SetupErrors = SetupErrors(),
    val bitmap: Bitmap? = null
)

data class SetupParams(
    val website: String = "",
    val prompt: String = "",
    val weight: Float = 1.5f
)

data class SetupErrors(
    val websiteError: String? = null,
    val promptError: String? = null
) {
    fun hasNoErrors() = websiteError == null && promptError == null
}
