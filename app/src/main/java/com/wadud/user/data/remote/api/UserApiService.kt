package com.wadud.user.data.remote.api

import com.wadud.user.domain.model.dto.ResponseDto
import retrofit2.http.GET

interface UserApiService {
    @GET("api")
    suspend fun getUser(): ResponseDto
}