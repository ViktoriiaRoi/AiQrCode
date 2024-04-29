package com.example.aiqrcode.helpers

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

interface QrCodeHelper {
    fun generateQrCode(content: String): Bitmap?
}

class QrCodeHelperImpl : QrCodeHelper {
    private val writer = QRCodeWriter()

    override fun generateQrCode(content: String): Bitmap? = try {
        val hints = mapOf(
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,
            EncodeHintType.MARGIN to 1
        )
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512, hints)
        val bitmap = Bitmap.createBitmap(768, 768, Bitmap.Config.RGB_565).apply {
            eraseColor(Color.parseColor("#808080"))
        }
        for (x in 0 until bitMatrix.width) {
            for (y in 0 until bitMatrix.height) {
                bitmap.setPixel(x + 128, y + 128, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        bitmap
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}