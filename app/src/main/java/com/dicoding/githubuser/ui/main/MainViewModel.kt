package com.dicoding.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.core.data.source.remote.network.ApiConfig
import com.dicoding.githubuser.core.data.source.remote.response.ItemsItem
import com.dicoding.githubuser.core.data.source.remote.response.SearchUserGithubResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
        private const val QUERY = "q"
    }

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _status = MutableLiveData<Boolean?>()
    val status: LiveData<Boolean?> = _status

    init {
        searchUser()
    }

    fun searchUser(query: String = QUERY) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchUserGithubResponse> {
            override fun onResponse(
                call: Call<SearchUserGithubResponse>,
                response: Response<SearchUserGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _status.value = true
                    _listUser.value = response.body()?.items
                } else {
                    _status.value = false
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserGithubResponse>, t: Throwable) {
                _isLoading.value = false
                _status.value = false
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}