package com.ahmrh.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUser (
        @Query("q") query: String
    ): Call<GitHubResponse>

    @GET("users")
    fun getAllUser(): Call<GitHubResponse>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username: String
    ): Call<List<UserItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserItem>>
}