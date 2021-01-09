package com.rizki.consumerapp

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.PersistableBundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rizki.consumerapp.databinding.ActivityFavBinding
import com.rizki.githubuserapp.adapter.FavAdapter
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.CONTENT_URI
import com.rizki.githubuserapp.helper.MappingHelper
import com.rizki.githubuserapp.model.UserItem
import com.rizki.githubuserapp.util.isInvisible
import com.rizki.githubuserapp.util.isVisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_STATE = "Extra_State"
    }

    private lateinit var favAdapter: FavAdapter
    private lateinit var binding: ActivityFavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            loadUserAsync()
        } else {
            savedInstanceState.getParcelableArrayList<UserItem>(EXTRA_STATE)?.also {
                favAdapter.listFavUser = it
            }
        }

        setAdapter()
        initDatabase()
    }

    private fun initDatabase() {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                loadUserAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    private fun setAdapter() {
        binding.rvFavAct.apply {
            layoutManager = LinearLayoutManager(this@FavActivity)
            setHasFixedSize(true)
            favAdapter = FavAdapter(this@FavActivity).apply {
                notifyDataSetChanged()
            }
            adapter = favAdapter
        }

    }



    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.isVisible()
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressBar.isInvisible()
            val users = deferredUsers.await()
            if (users.size > 0) {
                favAdapter.listFavUser = users
                favAdapter.notifyDataSetChanged()
            } else {
                favAdapter.listFavUser = ArrayList()
                showSnackBarMessage("Curently No Data")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelableArrayList(EXTRA_STATE, favAdapter.listFavUser)
    }

    private fun showSnackBarMessage(s: String) {
        Snackbar.make(binding.rvFavAct, s, Snackbar.LENGTH_LONG).show()
    }

}