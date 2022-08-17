package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.common.Resource

interface DiplomaExamRepository {

    suspend fun getHello(): Resource<String>
}