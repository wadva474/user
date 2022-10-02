package com.wadud.user.data.remote.api.dto

data class ResponseDto(
    val info: Info,
    val results: List<UserDto>
)