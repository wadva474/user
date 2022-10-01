package com.wadud.user.domain.model.dto

data class ResponseDto(
    val info: Info,
    val results: List<UserDto>
)