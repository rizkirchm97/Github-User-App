package com.rizki.githubuserapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FollowersItem (
    @field:SerializedName("avatar_url")
    val avatar: String? = null,

    @field:SerializedName("login")
    val username: String? = null
): Parcelable