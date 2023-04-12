package com.dicoding.githubuser.core.data.repository

import com.dicoding.githubuser.core.data.source.local.entity.FavoriteUserEntity
import com.dicoding.githubuser.core.data.source.local.room.FavoriteUserDao
import com.dicoding.githubuser.core.data.source.remote.network.ApiService

class FavoriteUserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
){
    suspend fun insertFavoriteUser(user: FavoriteUserEntity) {
        favoriteUserDao.insertFavoriteUser(user)
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