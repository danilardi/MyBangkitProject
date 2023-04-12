package com.dicoding.githubuser.ui.favoriteUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.core.data.repository.FavoriteUserRepository
import com.dicoding.githubuser.core.data.source.local.entity.FavoriteUserEntity

class FavoriteUserViewModel (private val favoriteUserRepository: FavoriteUserRepository): ViewModel(){
    fun getAllFavUser(): LiveData<List<FavoriteUserEntity>> = favoriteUserRepository.getFavoriteUser()

}