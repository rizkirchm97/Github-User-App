package com.rizki.githubuserapp.network


import com.rizki.githubuserapp.BuildConfig
import com.rizki.githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    companion object {
        private const val ACCESS_TOKEN = BuildConfig.GITHUB_TOKEN
    }

    @GET("search/users")
    @Headers("Authorization: token $ACCESS_TOKEN")
    fun getListUsers(@Query("q") usernameQuery: String?) : Call<UserResponse>
}