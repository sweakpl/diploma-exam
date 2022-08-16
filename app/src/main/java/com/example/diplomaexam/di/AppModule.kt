package com.example.diplomaexam.di

import com.example.diplomaexam.common.BASE_URL
import com.example.diplomaexam.data.remote.DiplomaExamApi
import com.example.diplomaexam.data.repository.DiplomaExamRepositoryImpl
import com.example.diplomaexam.domain.repository.DiplomaExamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDiplomaExamApi(): DiplomaExamApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DiplomaExamApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDiplomaExamRepository(api: DiplomaExamApi): DiplomaExamRepository {
        return DiplomaExamRepositoryImpl(api)
    }
}