package com.sweak.diplomaexam.di

import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.repository.*
import com.sweak.diplomaexam.domain.repository.*
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

    @Provides
    @ViewModelScoped
    fun provideQuestionsAnsweringRepository(
        api: DiplomaExamApi,
        userSessionManager: UserSessionManager
    ): QuestionsAnsweringRepository =
        QuestionsAnsweringRepositoryImpl(api, userSessionManager)

    @Provides
    @ViewModelScoped
    fun provideExamScoreRepository(
        api: DiplomaExamApi,
        userSessionManager: UserSessionManager
    ): ExamScoreRepository =
        ExamScoreRepositoryImpl(api, userSessionManager)
}