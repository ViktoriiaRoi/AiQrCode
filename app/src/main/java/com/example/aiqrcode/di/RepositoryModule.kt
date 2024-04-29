package com.example.aiqrcode.di

import com.example.aiqrcode.data.StableDiffusionRepository
import com.example.aiqrcode.data.StableDiffusionRepositoryImpl
import com.example.aiqrcode.data.StableDiffusionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideImageRepository(imageService: StableDiffusionService): StableDiffusionRepository {
        return StableDiffusionRepositoryImpl(imageService)
    }
}