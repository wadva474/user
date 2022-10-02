package com.wadud.user.domain.repository

import com.wadud.user.domain.model.User
import com.wadud.user.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchUser(
        fetchFromRemote: Boolean,
        query: String
    ) : Flow<Resource<List<User>>>
}