package com.example.egzamindyplomowy.di

import com.example.egzamindyplomowy.common.BASE_URL
import com.example.egzamindyplomowy.data.remote.DiplomaExamApi
import com.example.egzamindyplomowy.data.repository.DiplomaExamRepositoryImpl
import com.example.egzamindyplomowy.domain.repository.DiplomaExamRepository
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