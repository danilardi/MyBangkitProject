package com.dicoding.githubuser.core.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.githubuser.core.data.source.local.entity.FavoriteUserEntity
import com.dicoding.githubuser.core.data.source.local.room.FavoriteUserDao
import com.dicoding.githubuser.core.data.source.remote.network.ApiService

class FavoriteUserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
){
    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>> {
        return favoriteUserDao.getFavoriteUser()
    }

    suspend fun insertFavoriteUser(user: FavoriteUserEntity) {
        favoriteUserDao.insertFavoriteUser(user)
    }

    suspend fun deleteFavoriteUser(user: FavoriteUserEntity) {
        favoriteUserDao.deleteFavoriteUser(user)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity> {
        return favoriteUserDao.getFavoriteUserByUsername(username)
    }
    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(apiService, favoriteUserDao)
            }.also { instance = it }
    }
}