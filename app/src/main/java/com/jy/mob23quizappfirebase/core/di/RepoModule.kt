package com.jy.mob23quizappfirebase.core.di

import com.jy.mob23quizappfirebase.core.data.repo.UserRepo
import com.jy.mob23quizappfirebase.core.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideUserRepo(authService: AuthService): UserRepo = UserRepo(authService)
}