package com.sweak.diplomaexam.di

import android.app.Application
import com.sweak.diplomaexam.data.common.BASE_URL
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.local.UserSessionManagerImpl
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.repository.AuthenticationRepositoryImpl
import com.sweak.diplomaexam.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDiplomaExamApi(): DiplomaExamApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DiplomaExamApi::class.java)

    @Provides
    @Singleton
    fun provideUserSessionManager(app: Application): UserSessionManager =
        UserSessionManagerImpl(app)
}