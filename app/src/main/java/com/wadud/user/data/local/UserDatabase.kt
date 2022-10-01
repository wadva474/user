package com.wadud.user.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}