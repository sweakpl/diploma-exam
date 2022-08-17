package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.domain.repository.DiplomaExamRepository
import com.sweak.diplomaexam.presentation.ui.util.UiText
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
            Resource.Error(
                UiText.DynamicString(httpException.localizedMessage ?: httpException.message())
            )
        } catch (ioException: IOException) {
            Resource.Error(UiText.StringResource(R.string.cant_reach_server))
        }
    }
}