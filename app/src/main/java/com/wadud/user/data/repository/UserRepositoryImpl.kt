package com.wadud.user.data.repository

import com.wadud.user.data.local.UserDao
import com.wadud.user.data.mapper.toUser
import com.wadud.user.data.mapper.toUserEntity
import com.wadud.user.data.remote.api.UserApiService
import com.wadud.user.domain.model.User
import com.wadud.user.domain.repository.UserRepository
import com.wadud.user.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApiService,
    private val dao: UserDao,
) : UserRepository {
    override suspend fun fetchUser(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchUser(query)
            emit(Resource.Success(data = localListing.map { it.toUser() }))
            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Success(dao.searchUser(query).map {
                    it.toUser()
                }))
                emit(Resource.Loading(false))
                return@flow
            }
            if (isDbEmpty) {
                for (i in 0..3) {
                    fetchDataPopulateDb()
                }
            } else {
                fetchDataPopulateDb()
            }
        }

    }

    private suspend fun FlowCollector<Resource<List<User>>>.fetchDataPopulateDb() {
        val remoteListing = try {
            val response = userApi.getUser()
            response.results.map {
                it.toUserEntity()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Resource.Error("Error while Loading Data"))
            null
        }
        remoteListing?.let { listing ->
            dao.insertUser(*listing.toTypedArray())
            emit(Resource.Success(dao.searchUser("").map {
                it.toUser()
            }))
            emit(Resource.Loading(false))
        }
    }
}