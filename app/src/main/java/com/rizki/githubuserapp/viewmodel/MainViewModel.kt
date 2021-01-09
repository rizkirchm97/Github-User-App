package com.rizki.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizki.githubuserapp.model.UserItem
import com.rizki.githubuserapp.model.UserResponse
import com.rizki.githubuserapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainViewModel: ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserItem>>()

    fun setUsername(username: String?) {
        val listItem = ArrayList<UserItem>()
        val client = ApiConfig.getApiService().getListUsers(username)
        client.enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                try {
                    if (response.isSuccessful) {
                        val dataArray = response.body()?.items as ArrayList<UserItem>
                        for (items in dataArray) {
                            listItem.add(items)
                        }
                        listUser.postValue(listItem)
                    } else {
                        listItem.clear()
                        listUser.postValue(listItem)
                    }
                } catch (e: Exception) {
                    Log.d("Error Exception: ", e.message.toString())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("onFailure: ", t.message.toString())
            }
        })
    }

    fun getUser() : LiveData<ArrayList<UserItem>> = listUser
}