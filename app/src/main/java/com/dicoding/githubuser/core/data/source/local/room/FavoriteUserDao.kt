package com.dicoding.githubuser.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuser.core.data.source.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favoriteUser ORDER BY username ASC")
    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Delete
    suspend fun deleteFavoriteUser(user: FavoriteUserEntity)

    @Query("SELECT * FROM favoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>
}