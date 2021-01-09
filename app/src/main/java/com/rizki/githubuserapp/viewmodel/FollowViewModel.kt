package com.rizki.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizki.githubuserapp.model.FollowersItem
import com.rizki.githubuserapp.model.FollowingItem
import com.rizki.githubuserapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FollowViewModel: ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<FollowersItem>>()
    fun getFollowersData(): LiveData<ArrayList<FollowersItem>> = listFollowers
    val listFollowing = MutableLiveData<ArrayList<FollowingItem>>()
    fun getFollowingData(): LiveData<ArrayList<FollowingItem>> = listFollowing

    fun setFollowers(username: String?) {
        val listItem = ArrayList<FollowersItem>()
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<FollowersItem>>{
            override fun onResponse(
                call: Call<List<FollowersItem>>,
                response: Response<List<FollowersItem>>
            ) {
                try {
                    if (response.isSuccessful) {
                        Log.d("onResponse Success", response.message())
                        val dataArray = response.body() as ArrayList<FollowersItem>
                        for (items in dataArray) {
                            listItem.add(items)
                        }
                        listFollowers.postValue(listItem)
                    } else {
                        Log.d("onResponse !Success", response.message())
                    }
                } catch (e: Exception) {
                    Log.d("onResponse Exception", e.message.toString())
                }
            }

            override fun onFailure(call: Call<List<FollowersItem>>, t: Throwable) {
                Log.d("onFailure",t.message.toString())
                t.printStackTrace()
            }

        })
    }

    fun setFollowing(username: String?) {
        val listItem = ArrayList<FollowingItem>()
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<FollowingItem>>{
            override fun onResponse(
                call: Call<List<FollowingItem>>,
                response: Response<List<FollowingItem>>
            ) {
                try {
                    if (response.isSuccessful) {
                        val dataArray = response.body() as ArrayList<FollowingItem>
                        for (item in dataArray) {
                            listItem.add(item)
                        }
                        listFollowing.postValue(listItem)
                    } else {
                        Log.d("onResponse !Success", response.message())
                    }
                } catch (e: Exception) {
                    Log.d("onResponse Exception", e.message.toString())
                }
            }

            override fun onFailure(call: Call<List<FollowingItem>>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
                t.printStackTrace()
            }

        })
    }
}