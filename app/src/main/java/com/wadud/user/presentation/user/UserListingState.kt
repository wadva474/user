package com.wadud.user.presentation.user

import com.wadud.user.domain.model.User

data class UserListingState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)