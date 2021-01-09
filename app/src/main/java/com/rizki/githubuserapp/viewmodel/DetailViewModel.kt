package com.rizki.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizki.githubuserapp.model.UserItemDetail
import com.rizki.githubuserapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DetailViewModel: ViewModel() {
    val itemDetail = MutableLiveData<UserItemDetail>()

    fun setData(username: String?){
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserItemDetail>{
            override fun onResponse(
                call: Call<UserItemDetail>,
                response: Response<UserItemDetail>
            ) {
                try {
                    if (response.isSuccessful) {
                        Log.d("onResponse Success: ", response.message())
                        val data = response.body()
                        itemDetail.postValue(data)
                    } else {
                        Log.d("onResponse !Success: ", response.message())
                    }
                } catch (e: Exception) {
                    Log.d("Error Exception: ", e.message.toString())
                }
            }

            override fun onFailure(call: Call<UserItemDetail>, t: Throwable) {
                Log.d("onFailure: ", t.message.toString())
                t.printStackTrace()
            }
        })
    }

    fun getData(): LiveData<UserItemDetail> = itemDetail
}