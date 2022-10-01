package com.wadud.user.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Int? = null,
    val fullName: String,
    val email: String,
    val address: String,
    val profileImage: String
)