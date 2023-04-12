package com.dicoding.githubuser.ui.detailUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.core.data.source.remote.network.ApiConfig
import com.dicoding.githubuser.core.data.source.remote.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _status = MutableLiveData<Boolean?>()
    val status: LiveData<Boolean?> = _status

    fun getUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _status.value = true
                    _user.value = response.body()
                } else {
                    _status.value = false
                    Log.d(TAG, "onFailure1: ${query}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _status.value = false
                Log.d(TAG, "onFailure2: ${t.message}")
            }
        })
    }
}