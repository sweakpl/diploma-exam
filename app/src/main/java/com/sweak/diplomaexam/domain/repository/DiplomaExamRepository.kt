package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.common.Resource

interface DiplomaExamRepository {

    suspend fun getHello(): Resource<String>
}