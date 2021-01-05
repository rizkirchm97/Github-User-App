package com.rizki.githubuserapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (
    var id: Int = 0,
    var username: String? = null,
    var name: String? = null,
    var avatar: String? = null,
    var location: String? = null,
    var company: String? = null,
    var email: String? = null,
    var website: String? = null,
    var followers: Int = 0,
    var following: Int = 0,
    var repository: Int = 0
        ): Parcelable