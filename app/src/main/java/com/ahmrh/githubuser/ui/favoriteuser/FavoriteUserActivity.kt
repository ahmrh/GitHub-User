package com.ahmrh.githubuser.ui.favoriteuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmrh.githubuser.R
import com.ahmrh.githubuser.database.FavoriteUser
import com.ahmrh.githubuser.database.ListUserValue
import com.ahmrh.githubuser.databinding.ActivityFavoriteUserBinding
import com.ahmrh.githubuser.helper.ViewModelFactory
import com.ahmrh.githubuser.ui.adapter.ListUserAdapter
import com.ahmrh.githubuser.ui.setting.SettingActivity
import com.ahmrh.githubuser.ui.user.UserActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityFavoriteUserBinding
    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    companion object{
        const val  TAG = "FavoriteUserActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        favoriteUserViewModel.getAllFavoriteUsers().observe(this){
            if(it != null){
                setUsersData(it)
            }
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
        binding.rvUser.layoutManager = LinearLayoutManager(this)

        val users =  ArrayList<ListUserValue>()
        for (user in listUser){
            users.add(ListUserValue(user.username, user.avatarUrl))
        }

        val adapter = ListUserAdapter(users)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUserValue) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: ListUserValue) {
        val detailIntent = Intent(this@FavoriteUserActivity, UserActivity::class.java)
        detailIntent.putExtra(UserActivity.USERNAME, user.login)
        detailIntent.putExtra(UserActivity.AVATAR_URL, user.avatarUrl)
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