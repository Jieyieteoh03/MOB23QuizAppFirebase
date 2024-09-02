package com.jy.mob23quizappfirebase.core.di

import android.content.Context
import com.jy.mob23quizappfirebase.core.services.AuthService
import com.jy.mob23quizappfirebase.core.services.StorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthService(
        @ApplicationContext context: Context
    ): AuthService = AuthService(context)

    @Provides
    @Singleton
    fun provideStorageService(
        @ApplicationContext context: Context
    ): StorageService = StorageService(context)
}