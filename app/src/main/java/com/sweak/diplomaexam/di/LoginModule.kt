package com.sweak.diplomaexam.di

import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.repository.AuthenticationRepositoryImpl
import com.sweak.diplomaexam.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    @ViewModelScoped
    fun provideAuthenticationRepository(
        api: DiplomaExamApi,
        userSessionManager: UserSessionManager
    ): AuthenticationRepository =
        AuthenticationRepositoryImpl(api, userSessionManager)
}