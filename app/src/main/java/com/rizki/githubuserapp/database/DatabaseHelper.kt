package com.rizki.githubuserapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.AVATAR
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.TABLE_NAME
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.USERNAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbgithubuserapp"

        private const val DATABASE_VERSION = 1

        private const val CREATE_TABLE_GITHUBUSERAPP = "CREATE TABLE $TABLE_NAME" +
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $USERNAME TEXT NOT NULL," +
                " $AVATAR TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_GITHUBUSERAPP)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}