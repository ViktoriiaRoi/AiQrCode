package com.example.aiqrcode.di

import com.example.aiqrcode.helpers.ImageHelper
import com.example.aiqrcode.helpers.ImageHelperImpl
import com.example.aiqrcode.helpers.QrCodeHelper
import com.example.aiqrcode.helpers.QrCodeHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelpersModule {

    @Singleton
    @Provides
    fun provideQrCodeHelper(): QrCodeHelper {
        return QrCodeHelperImpl()
    }

    @Singleton
    @Provides
    fun provideImageHelper(): ImageHelper {
        return ImageHelperImpl()
    }
}