package com.dicoding.githubuser.core.di

import android.content.Context
import com.dicoding.githubuser.core.data.repository.FavoriteUserRepository
import com.dicoding.githubuser.core.data.source.local.room.FavoriteUserDatabase
import com.dicoding.githubuser.core.data.source.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        return FavoriteUserRepository.getInstance(apiService, dao)
    }
}