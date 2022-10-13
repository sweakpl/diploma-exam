package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.login.LoginRequest
import com.sweak.diplomaexam.domain.model.login.LoginResponse

interface AuthenticationRepository {

    suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse>
}