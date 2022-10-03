package com.wadud.user.presentation.user

sealed class UserListingEvent {
    object Refresh : UserListingEvent()
    data class OnQueryChange(val query: String) :UserListingEvent()
}