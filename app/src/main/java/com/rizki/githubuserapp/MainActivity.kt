package com.rizki.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizki.githubuserapp.adapter.UserAdapter
import com.rizki.githubuserapp.databinding.ActivityMainBinding
import com.rizki.githubuserapp.databinding.BaseNoDataBinding
import com.rizki.githubuserapp.databinding.ListUserLayoutBinding
import com.rizki.githubuserapp.model.UserItem
import com.rizki.githubuserapp.ui.DetailActivity
import com.rizki.githubuserapp.ui.FavActivity
import com.rizki.githubuserapp.ui.SettingActivity
import com.rizki.githubuserapp.util.isGone
import com.rizki.githubuserapp.util.isVisible
import com.rizki.githubuserapp.util.setTypeFace
import com.rizki.githubuserapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var baseNoDataBinding: BaseNoDataBinding
    private lateinit var listUserBinding: ListUserLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initViewMode()
        startSearching()
        initRv()
        customView()
    }

    private fun initViewMode() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        mainViewModel.getUser().observe(this, { userItems ->
            if (userItems.isNullOrEmpty()) {
                userAdapter.clearData()
                userAdapter.notifyDataSetChanged()
                binding.searchView.clearFocus()
                binding.progressBar.isGone()
            } else {
                if (!userItems.isNullOrEmpty()) {
                    userAdapter.setData(userItems)
                    binding.progressBar.isGone()
                }
            }

        })
    }

    private fun startSearching() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                setQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                setQuery(newText)
                return false
            }

        })
    }

    private fun setQuery(s: String?) {
        s?.let {
            if (it.isNotEmpty()) {
                mainViewModel.setUsername(s)
                binding.progressBar.isVisible()
                setNoData(false)
            } else {
                mainViewModel.setUsername(s)
                binding.progressBar.isVisible()
                setNoData(true)
            }
        }
    }

    private fun initRv() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        listUserBinding.rvUserList.layoutManager = LinearLayoutManager(this)
        listUserBinding.rvUserList.adapter = userAdapter
        userAdapter.setOnItemClickCallback( object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserItem) {
                showSelectedUser(data)
            }

        })

    }

    private fun showSelectedUser(data: UserItem) {
        val sendObject = Intent(this@MainActivity, DetailActivity::class.java)
        sendObject.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(sendObject)

    }

    private fun initView() {
        supportActionBar?.title = getString(R.string.user_app)
        baseNoDataBinding = binding.noDataResult
        listUserBinding = binding.showRv
    }

    private fun customView() {
        val typeFace = ResourcesCompat.getFont(applicationContext, R.font.myfont)
        baseNoDataBinding.tvResultAppear.typeface = typeFace
        binding.searchView.setTypeFace(typeFace)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_container_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.actionMainFavorite -> startActivity(Intent(applicationContext, FavActivity::class.java))
            R.id.actionMainSetting -> startActivity(Intent(applicationContext, SettingActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setNoData(s: Boolean) {
        baseNoDataBinding.tvResultAppear.visibility = if (s) VISIBLE else INVISIBLE
        baseNoDataBinding.ivOctocat.visibility = if (s) VISIBLE else INVISIBLE
    }
}