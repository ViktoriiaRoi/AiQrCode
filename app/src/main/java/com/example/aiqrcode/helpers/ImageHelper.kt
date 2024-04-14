package com.example.aiqrcode.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

interface ImageHelper {
    fun encodeImageString(bitmap: Bitmap): String
    fun decodeImageString(imageString: String): Bitmap?
}

class ImageHelperImpl : ImageHelper {
    override fun encodeImageString(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun decodeImageString(imageString: String): Bitmap? = try {
        val bytes = Base64.decode(imageString, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
