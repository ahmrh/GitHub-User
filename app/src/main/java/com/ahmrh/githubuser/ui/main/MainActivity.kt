package com.ahmrh.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmrh.githubuser.R
import com.ahmrh.githubuser.api.UserItem
import com.ahmrh.githubuser.databinding.ActivityMainBinding
import com.ahmrh.githubuser.ui.adapter.ListUserAdapter
import com.ahmrh.githubuser.ui.setting.SettingActivity
import com.ahmrh.githubuser.ui.favoriteuser.FavoriteUserActivity
import com.ahmrh.githubuser.ui.user.UserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel.listUser.observe(this) { listUser ->
            setUsersData(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                mainViewModel.searchUser(query)

                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_favorite->{
                startActivity(Intent(this@MainActivity, FavoriteUserActivity::class.java))
            }
            R.id.menu_setting-> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
            }
        }
        return true
    }

    private fun setUsersData(listUser: List<UserItem>) {

        val adapter = ListUserAdapter(listUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UserItem) {
        val detailIntent = Intent(this@MainActivity, UserActivity::class.java)
        detailIntent.putExtra(UserActivity.USERNAME, user.login)
        startActivity(detailIntent)

    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}