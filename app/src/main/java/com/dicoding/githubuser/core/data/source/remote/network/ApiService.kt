package com.dicoding.githubuser.core.data.source.remote.network

import com.dicoding.githubuser.core.data.source.remote.response.DetailUserResponse
import com.dicoding.githubuser.core.data.source.remote.response.ItemsItem
import com.dicoding.githubuser.core.data.source.remote.response.SearchUserGithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ): Call<SearchUserGithubResponse>

    @GET("users/{id}")
    fun getUser(
        @Path("id") id: String
    ): Call<DetailUserResponse>

    @GET("users/{id}/followers")
    fun getFollowers(
        @Path("id") id: String
    ): Call<List<ItemsItem>>

    @GET("users/{id}/following")
    fun getFollowing(
        @Path("id") id: String
    ): Call<List<ItemsItem>>

}