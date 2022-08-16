package com.example.diplomaexam.data.repository

import android.util.Log
import com.example.diplomaexam.data.remote.DiplomaExamApi
import com.example.diplomaexam.domain.repository.DiplomaExamRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DiplomaExamRepositoryImpl @Inject constructor(
    private val api: DiplomaExamApi
): DiplomaExamRepository {

    override suspend fun getHello(): String {
        var result = ""

        try {
            result = api.getHello().body().toString()
        } catch (httpException: HttpException) {
            Log.e("DiplomaExamRepository", "Caught HttpException!")
        } catch (ioException: IOException) {
            Log.e("DiplomaExamRepository", "Caught IOException!")
        }

        return result
    }
}