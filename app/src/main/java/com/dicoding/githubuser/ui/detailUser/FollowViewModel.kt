package com.dicoding.githubuser.ui.detailUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.core.data.source.remote.network.ApiConfig
import com.dicoding.githubuser.core.data.source.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower: LiveData<List<ItemsItem>> = _listFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _status = MutableLiveData<Boolean?>()
    val status: LiveData<Boolean?> = _status

    fun getFollower(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _status.value = true
                    _listFollower.value = response.body()
                } else {
                    _status.value = false
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _status.value = false
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}