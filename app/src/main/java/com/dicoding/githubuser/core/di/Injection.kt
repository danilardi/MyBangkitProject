package com.dicoding.githubuser.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.githubuser.core.data.repository.FavoriteUserRepository
import com.dicoding.githubuser.core.data.source.local.room.FavoriteUserDatabase
import com.dicoding.githubuser.core.data.source.remote.network.ApiConfig
import com.dicoding.githubuser.ui.setting.SettingPreferences

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        return FavoriteUserRepository.getInstance(apiService, dao)
    }
    fun provideSettingPref(dataStore: DataStore<Preferences>): SettingPreferences {
        return SettingPreferences.getInstance(dataStore)
    }
}