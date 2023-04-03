package com.ahmrh.githubuser.ui.favoriteuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.ahmrh.githubuser.R
import com.ahmrh.githubuser.api.UserItem
import com.ahmrh.githubuser.database.FavoriteUser
import com.ahmrh.githubuser.databinding.ActivityFavoriteUserBinding
import com.ahmrh.githubuser.ui.adapter.ListUserAdapter
import com.ahmrh.githubuser.ui.setting.SettingActivity
import com.ahmrh.githubuser.ui.user.UserActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityFavoriteUserBinding
    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        favoriteUserViewModel.listFavoriteUser.observe(this){
            setUsersData(it)
        }

        favoriteUserViewModel.isLoading.observe(this){
            showLoading(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_setting-> {
                startActivity(Intent(this@FavoriteUserActivity, SettingActivity::class.java))
            }
        }
        return true
    }
    private fun setUsersData(listUser: List<FavoriteUser>) {

        val adapter = ListUserAdapter(listUser as List<UserItem>)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UserItem) {
        val detailIntent = Intent(this@FavoriteUserActivity, UserActivity::class.java)
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