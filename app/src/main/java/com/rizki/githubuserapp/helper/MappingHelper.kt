package com.rizki.githubuserapp.helper

import android.database.Cursor
import android.provider.BaseColumns._ID
import com.rizki.githubuserapp.database.UserContract
import com.rizki.githubuserapp.model.UserItem

object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?) : ArrayList<UserItem> {
        val userList = ArrayList<UserItem>()

        userCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val username = getString(getColumnIndexOrThrow(UserContract.UsersColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(UserContract.UsersColumns.AVATAR))
                userList.add(UserItem(avatar, username, id))
            }
        }
        return userList
    }

    fun mapCursorToObject(userCursor: Cursor?): UserItem {
        var user = UserItem()
        userCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(_ID))
            val username = getString(getColumnIndexOrThrow(UserContract.UsersColumns.USERNAME))
            val avatar = getString(getColumnIndexOrThrow(UserContract.UsersColumns.AVATAR))
            user = UserItem(avatar, username, id)
        }
        return user
    }
}