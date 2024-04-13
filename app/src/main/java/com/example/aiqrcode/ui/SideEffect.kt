package com.example.aiqrcode.ui

sealed interface SideEffect {
    data class ShowError(val message: String) : SideEffect
}