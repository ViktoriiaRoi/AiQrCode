package com.example.aiqrcode

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun sendRequest() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(loading = true) }

        val imageBytes = ApiService.sendRequest()
        if (imageBytes != null) {
            try {
                val jsonResponse = String(imageBytes, Charsets.UTF_8)
                val jsonObject = JSONObject(jsonResponse)
                val imageArray = jsonObject.getJSONArray("images")
                val base64Image = imageArray.getString(0)
                val decodedImageBytes = Base64.decode(base64Image, Base64.DEFAULT)

                val bitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
                _uiState.update { it.copy(bitmap = bitmap, loading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false) } // Failed to decode image bytes
            }
        } else {
            _uiState.update { it.copy(loading = false) } // Failed to retrieve image bytes
        }
    }
}