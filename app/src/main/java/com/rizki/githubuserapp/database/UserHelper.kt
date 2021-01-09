package com.rizki.githubuserapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.TABLE_NAME
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.USERNAME
import java.sql.SQLException

class UserHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: UserHelper? = null
        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }

        private lateinit var database: SQLiteDatabase
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }
    fun queryByUsername(user: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$USERNAME = ?",
            arrayOf(user),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteByUsername(id: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}