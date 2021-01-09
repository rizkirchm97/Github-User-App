package com.rizki.githubuserapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (
    var id: Int = 0,
    var username: String? = null,
    var avatar: String? = null,
): Parcelable