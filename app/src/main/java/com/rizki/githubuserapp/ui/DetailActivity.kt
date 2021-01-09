package com.rizki.githubuserapp.ui

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.rizki.githubuserapp.R
import com.rizki.githubuserapp.adapter.SectionsPagerAdapter
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.AVATAR
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.CONTENT_URI
import com.rizki.githubuserapp.database.UserContract.UsersColumns.Companion.USERNAME
import com.rizki.githubuserapp.database.UserHelper
import com.rizki.githubuserapp.databinding.ActivityDetailBinding
import com.rizki.githubuserapp.databinding.LayoutProfiledetailBinding
import com.rizki.githubuserapp.model.UserItem
import com.rizki.githubuserapp.util.isGone
import com.rizki.githubuserapp.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_POSITION = "extra_position"

    }
    private lateinit var binding: ActivityDetailBinding
    private lateinit var profileDetailBinding: LayoutProfiledetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userHelper: UserHelper

    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initView()
        initViewModel()
        getIntentObject()
        setFAB()
        checkFavorite()
        setStatusFavorite(statusFavorite)
        setUsername()
        setUserData()
        setViewPager()


    }

    private fun checkFavorite() {
        val dataUser = intent.getParcelableExtra<UserItem>(EXTRA_USER) as UserItem
        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()
        val cursor: Cursor = userHelper.queryByUsername(dataUser.login.toString())
        if (cursor.moveToNext()) {
            statusFavorite = true
            setStatusFavorite(true)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.scondary_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.actionSecondarySetting -> startActivity(Intent(applicationContext, SettingActivity::class.java))
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }



    private fun initView() {
        supportActionBar?.title = getString(R.string.title_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        profileDetailBinding = binding.layoutProfile
    }


    private fun initViewModel() {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
    }

    private fun getIntentObject() {
        val intentObject = intent.getParcelableExtra<UserItem>(EXTRA_USER) as UserItem
        Glide.with(applicationContext)
            .load(intentObject.avatarUrl)
            .apply(RequestOptions().override(120, 120))
            .into(profileDetailBinding.civImgProfileDetail)
        profileDetailBinding.tvUsernameDetail.text = intentObject.login
    }

    private fun setUsername() {
        val setUserName = intent.getParcelableExtra<UserItem>(EXTRA_USER) as UserItem
        detailViewModel.setData(setUserName.login)
    }

    private fun setUserData() {
        detailViewModel.getData().observe(this, { setData ->
            if (setData != null) {
                binding.progressBar.isGone()
                profileDetailBinding.apply {
                    tvNameDetail.text = setData.name
                    tvFollowers.text = setData.followers.toString()
                    tvFollowing.text = setData.following.toString()
                    tvLocationId.text = setData.location ?: getString(R.string.empty)
                    tvCompany.text = setData.company ?: getString(R.string.empty)
                    tvEmail.text = setData.email ?: getString(R.string.empty)
                    tvRepository.text = setData.publicRepos.toString()
                }
            }
        })
    }


    private fun setViewPager() {
        val setViewpager = intent.getParcelableExtra<UserItem>(EXTRA_USER) as UserItem
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = setViewpager.login
        binding.vpDetail.adapter = sectionsPagerAdapter
        binding.tlDetail.setupWithViewPager(binding.vpDetail)
    }



    private fun setFAB() {
        val dataUser = intent.getParcelableExtra<UserItem>(EXTRA_USER) as UserItem
        binding.fabAddFav.setOnClickListener {
            if (statusFavorite) {
                userHelper.deleteByUsername(dataUser.login.toString())
                showSnackBar("User has been deleted from your Favorite")
                setStatusFavorite(false)
                statusFavorite = true
            } else {
                val values = ContentValues()
                values.put(USERNAME, dataUser.login)
                values.put(AVATAR, dataUser.avatarUrl)
                statusFavorite = false
                setStatusFavorite(true)
                contentResolver.insert(CONTENT_URI, values)
                showSnackBar("User has been added to Favorite")


            }
        }

    }

    private fun showSnackBar(s:String) {
        Snackbar.make(binding.fabAddFav, s, Snackbar.LENGTH_LONG).show()
    }


    private fun setStatusFavorite(statusFavorite: Boolean): Boolean {
        return if (statusFavorite){
            binding.fabAddFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            true
        } else {
            binding.fabAddFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            false
        }
    }

}