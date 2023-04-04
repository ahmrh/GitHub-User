package com.ahmrh.githubuser.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.ahmrh.githubuser.R
import com.ahmrh.githubuser.api.UserResponse
import com.ahmrh.githubuser.database.FavoriteUser
import com.ahmrh.githubuser.databinding.ActivityUserBinding
import com.ahmrh.githubuser.helper.ViewModelFactory
import com.ahmrh.githubuser.ui.adapter.SectionsPagerAdapter
import com.ahmrh.githubuser.ui.setting.SettingActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val userViewModel by viewModels<UserViewModel>{
        ViewModelFactory.getInstance(application)
    }
    companion object {
        const val USERNAME = "extra_name"
        const val AVATAR_URL = "extra_avatar_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.detail.observe(this) { detail ->
            setDetailUser(detail)
        }

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val username = intent.getStringExtra(USERNAME).toString()

        userViewModel.isFavorite(username).observe(this){
            if(it == null){
                setFavoriteButton()
            } else{
                setDeleteFavoriteButton(it)
            }
        }

        initData()
        setViewPager()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail User"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_setting-> {
                startActivity(Intent(this@UserActivity, SettingActivity::class.java))
            }
        }
        return true
    }
    private fun setDeleteFavoriteButton(user: FavoriteUser){
        binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_white_24)
        binding.btnFavorite.setOnClickListener{
            userViewModel.delete(user)
        }
    }

    private fun setFavoriteButton() {
        val username = intent.getStringExtra(USERNAME).toString()
        val avatarUrl = intent.getStringExtra(AVATAR_URL).toString()

        binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_white_24)
        binding.btnFavorite.setOnClickListener {
            val newFavoriteUser = FavoriteUser(username, avatarUrl)

            userViewModel.insert(newFavoriteUser)
        }
    }

    private fun initData(){
        val username = intent.getStringExtra(USERNAME).toString()
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show()
        userViewModel.getDetail(username)
        userViewModel.getFollower(username)
        userViewModel.getFollowing(username)
    }

    private fun setDetailUser(detail : UserResponse){
        binding.tvName.text = detail.name
        binding.tvUsername.text = "@${detail.login}"
        Glide.with(this)
            .load(detail.avatarUrl)
            .into(binding.imgUser)

        binding.tvFollower.text = "${detail.followers} followers"
        binding.tvFollowing.text = "${detail.following} following"
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabsUser, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.layoutDetail.visibility = View.GONE
            binding.layoutFollow.visibility = View.GONE
            binding.btnFavorite.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.layoutDetail.visibility = View.VISIBLE
            binding.layoutFollow.visibility = View.VISIBLE
            binding.btnFavorite.visibility = View.VISIBLE
        }
    }
}