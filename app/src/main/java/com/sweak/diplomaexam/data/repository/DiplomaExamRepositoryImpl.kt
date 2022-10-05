package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.Error
import com.sweak.diplomaexam.domain.repository.DiplomaExamRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DiplomaExamRepositoryImpl @Inject constructor(
    private val api: DiplomaExamApi
): DiplomaExamRepository {

    override suspend fun getHello(): Resource<String> {
        return try {
            Resource.Success(api.getHello().body().toString())
        } catch (httpException: HttpException) {
            Resource.Failure(
                Error.HttpError(
                    httpException.code(),
                    httpException.localizedMessage ?: httpException.message
                )
            )
        } catch (ioException: IOException) {
            Resource.Failure(Error.IOError(ioException.message))
        }
    }
}