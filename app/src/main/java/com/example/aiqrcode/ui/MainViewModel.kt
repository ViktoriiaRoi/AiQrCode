package com.example.aiqrcode.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiqrcode.data.StableDiffusionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: StableDiffusionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<SideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    fun sendRequest() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(bitmap = null) }

        val result = imageRepository.generateImage()
        result.onSuccess { bytes ->
            val bitmap = convertToBitmap(bytes)
            if (bitmap != null) {
                _uiState.update { it.copy(bitmap = bitmap) }
            } else {
                _sideEffects.send(SideEffect.ShowError("Failed to decode image"))
            }
        }.onFailure { error ->
            _sideEffects.send(SideEffect.ShowError(error.message ?: "Unexpected error"))
        }
    }

    private fun convertToBitmap(bytes: ByteArray): Bitmap? {
        return try {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            null
        }
    }
}