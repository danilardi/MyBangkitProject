package com.dicoding.githubuser.ui.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.core.di.Injection
import com.dicoding.githubuser.ui.ViewModelFactory
import com.dicoding.githubuser.ui.detailUser.DetailUserViewModel
import com.dicoding.githubuser.ui.favoriteUser.FavoriteUserViewModel
import com.dicoding.githubuser.ui.main.MainActivity
import com.dicoding.githubuser.ui.main.MainViewModel

class SettingViewModelFactory private constructor(private val pref: SettingPreferences):
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SettingViewModelFactory? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SettingViewModelFactory(Injection.provideSettingPref(dataStore))
            }.also { instance = it }
    }
}