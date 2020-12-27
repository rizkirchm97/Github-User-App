package com.rizki.githubuserapp.database

import android.provider.BaseColumns

internal class UserContract {
    internal class UsersColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR = "avatar"
            const val LOCATION = "location"
            const val COMPANY = "company"
            const val EMAIL = "email"
            const val WEBSITE = "website"
            const val FOLLOWERS_COUNT = "followers_count"
            const val FOLLOWING_COUNT = "following_count"
            const val REPOSITORY_COUNT = "repository_count"
        }
    }
}