package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.model.login.LoginResponse

interface AuthenticationRepository {

    suspend fun login(
        email: String,
        password: String,
        selectedUserRole: UserRole
    ): Resource<LoginResponse>
}