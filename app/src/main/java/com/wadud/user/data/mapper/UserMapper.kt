package com.wadud.user.data.mapper

import com.wadud.user.data.local.UserEntity
import com.wadud.user.domain.model.User
import com.wadud.user.data.remote.api.dto.UserDto

fun UserEntity.toUser(): User {
    return User(
        fullName, email, address, profileImage
    )
}


fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        fullName = name.title.plus(" ").plus(name.first).plus(" ").plus(name.last),
        email = email,
        profileImage = picture.large,
        address = "${location.street.number}".plus(",")
            .plus(location.street.name.plus(" ").plus(location.city)
                .plus(" ").plus(location.state).plus(" ").plus(location.country)
        )
    )
}