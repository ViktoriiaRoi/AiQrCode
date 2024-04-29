package com.example.aiqrcode.di

import android.content.Context
import com.example.aiqrcode.helpers.GalleryHelper
import com.example.aiqrcode.helpers.GalleryHelperImpl
import com.example.aiqrcode.helpers.ImageConverter
import com.example.aiqrcode.helpers.ImageConverterImpl
import com.example.aiqrcode.helpers.QrCodeHelper
import com.example.aiqrcode.helpers.QrCodeHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideGalleryHelper(@ApplicationContext appContext: Context): GalleryHelper {
        return GalleryHelperImpl(appContext)
    }

    @Singleton
    @Provides
    fun provideImageConverter(): ImageConverter {
        return ImageConverterImpl()
    }
}