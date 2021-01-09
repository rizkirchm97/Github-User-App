package com.rizki.githubuserapp.network


import com.rizki.githubuserapp.BuildConfig
import com.rizki.githubuserapp.model.FollowersItem
import com.rizki.githubuserapp.model.FollowingItem
import com.rizki.githubuserapp.model.UserItemDetail
import com.rizki.githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        private const val ACCESS_TOKEN = BuildConfig.GITHUB_TOKEN
    }

    @GET("search/users")
    @Headers("Authorization: token $ACCESS_TOKEN")
    fun getListUsers(@Query("q") usernameQuery: String?) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $ACCESS_TOKEN")
    fun getUserDetail(@Path("username") usernameDetail: String?) : Call<UserItemDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $ACCESS_TOKEN")
    fun getUserFollowers(@Path("username") userFollowers: String?): Call<List<FollowersItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $ACCESS_TOKEN")
    fun getUserFollowing(@Path("username") userFollowing: String?): Call<List<FollowingItem>>
}