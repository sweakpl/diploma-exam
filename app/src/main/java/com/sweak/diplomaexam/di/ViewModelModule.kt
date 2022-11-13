package com.sweak.diplomaexam.di

import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.repository.AuthenticationRepositoryImpl
import com.sweak.diplomaexam.data.repository.LobbyRepositoryImpl
import com.sweak.diplomaexam.data.repository.QuestionsDrawRepositoryImpl
import com.sweak.diplomaexam.data.repository.SessionSelectionRepositoryImpl
import com.sweak.diplomaexam.domain.repository.AuthenticationRepository
import com.sweak.diplomaexam.domain.repository.LobbyRepository
import com.sweak.diplomaexam.domain.repository.QuestionsDrawRepository
import com.sweak.diplomaexam.domain.repository.SessionSelectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideAuthenticationRepository(
        api: DiplomaExamApi,
        userSessionManager: UserSessionManager
    ): AuthenticationRepository =
        AuthenticationRepositoryImpl(api, userSessionManager)

    @Provides
    @ViewModelScoped
    fun provideSessionSelectionRepository(
        api: DiplomaExamApi,
        userSessionManager: UserSessionManager
    ): SessionSelectionRepository =
        SessionSelectionRepositoryImpl(api, userSessionManager)

    @Provides
    @ViewModelScoped
    fun provideLobbyRepository(
        api: DiplomaExamApi,
        userSessionManager: UserSessionManager
    ): LobbyRepository =
        LobbyRepositoryImpl(api, userSessionManager)

    @Provides
    @ViewModelScoped
    fun provideQuestionsDrawRepository(
        api: DiplomaExamApi,
        userSessionManager: UserSessionManager
    ): QuestionsDrawRepository =
        QuestionsDrawRepositoryImpl(api, userSessionManager)
}