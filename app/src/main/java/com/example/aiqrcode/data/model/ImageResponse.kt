package com.example.aiqrcode.data.model

data class ImageResponse(
    val images: List<String>
) {
    fun getFirstImage() = images.firstOrNull()
}