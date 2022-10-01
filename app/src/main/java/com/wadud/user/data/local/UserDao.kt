package com.wadud.user.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM userentity")
    suspend fun clearAllUser()

    @Query(
        """SELECT * FROM userentity WHERE LOWER(fullName) LIKE '%'|| LOWER(:query) || '%' """
    )
    suspend fun searchUser(query: String): List<UserEntity>
}