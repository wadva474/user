package com.wadud.user.presentation.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wadud.user.domain.repository.UserRepository
import com.wadud.user.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(UserListingState())

    private var searchJob: Job? = null


    init {
        getUserListing()
    }

    fun onEvent(event: UserListingEvent) {
        when (event) {
            is UserListingEvent.OnQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    getUserListing()
                }

            }
            is UserListingEvent.Refresh -> {
                getUserListing(fetchFromRemote = true)
            }
        }
    }


    private fun getUserListing(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            userRepository.fetchUser(fetchFromRemote, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { userListing ->
                            state = state.copy(users = userListing)
                        }
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }
}