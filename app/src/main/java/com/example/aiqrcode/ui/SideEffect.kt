package com.example.aiqrcode.ui

sealed interface SideEffect {
    data class ShowMessage(val message: String) : SideEffect
}