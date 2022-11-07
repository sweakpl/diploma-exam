package com.sweak.diplomaexam.di

import android.app.Application
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.local.UserSessionManagerImpl
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://diploma-examination-system.herokuapp.com/api/v1/"

    @Provides
    @Singleton
    fun provideDiplomaExamApi(baseUrl: String): DiplomaExamApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DiplomaExamApi::class.java)

    @Provides
    @Singleton
    fun provideUserSessionManager(app: Application): UserSessionManager =
        UserSessionManagerImpl(app)
}