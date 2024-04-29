package com.example.aiqrcode.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiqrcode.data.StableDiffusionRepository
import com.example.aiqrcode.helpers.ImageConverter
import com.example.aiqrcode.helpers.QrCodeHelper
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
    private val imageRepository: StableDiffusionRepository,
    private val qrCodeHelper: QrCodeHelper,
    private val imageConverter: ImageConverter
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<SideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    private val params get() = _uiState.value.setupParams

    fun updateParams(params: SetupParams) = viewModelScope.launch {
        _uiState.update { it.copy(setupParams = params) }
    }

    fun validateFields(): Boolean {
        val websiteError = if (params.website.isBlank()) "Cannot be empty" else null
        val promptError = if (params.prompt.isBlank()) "Cannot be empty" else null
        val errors = SetupErrors(websiteError, promptError)

        if (errors.hasNoErrors()) {
            return true
        } else {
            _uiState.update { it.copy(setupErrors = errors) }
            return false
        }
    }

    fun sendRequest() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(bitmap = null) }

        val qrCodeImage = qrCodeHelper.generateQrCode(params.website)
        if (qrCodeImage == null) {
            postError("Failed to generate QR code")
            return@launch
        }

        val encodedImage = imageConverter.encodeImageString(qrCodeImage)
        imageRepository.generateImage(params.prompt, encodedImage, params.weight)
            .onSuccess { processImage(it) }
            .onFailure { postError(it.message ?: "Unexpected error") }
    }

    private suspend fun processImage(imageString: String) {
        val decoded = imageConverter.decodeImageString(imageString)
        if (decoded != null) {
            _uiState.update { it.copy(bitmap = decoded) }
        } else {
            postError("Failed to decode image")
        }
    }

    private suspend fun postError(message: String) {
        _sideEffects.send(SideEffect.ShowError(message))
    }
}