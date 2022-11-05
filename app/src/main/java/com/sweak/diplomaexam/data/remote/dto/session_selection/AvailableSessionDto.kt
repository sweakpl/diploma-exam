package com.sweak.diplomaexam.data.remote.dto.session_selection

import com.sweak.diplomaexam.data.remote.dto.common.UserDto
import com.sweak.diplomaexam.data.remote.dto.common.toUser
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession

data class AvailableSessionDto(
    val id: Int,
    val userDtos: List<UserDto>
)

fun AvailableSessionDto.toAvailableSession(): AvailableSession =
    AvailableSession(
        this.id,
        this.userDtos.find {
            it.toUser().role == UserRole.USER_STUDENT
        }?.email ?: ""
    )