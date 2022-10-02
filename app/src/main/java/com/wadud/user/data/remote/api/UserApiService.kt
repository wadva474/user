package com.wadud.user.data.remote.api

import com.wadud.user.data.remote.api.dto.ResponseDto
import retrofit2.http.GET

interface UserApiService {
    @GET("api")
    suspend fun getUser(): ResponseDto
}