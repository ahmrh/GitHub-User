package com.ahmrh.githubuser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.ahmrh.githubuser.databinding.ActivityUserBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val userViewModel by viewModels<UserViewModel>()

    companion object {
        const val USERNAME = "extra_name"

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

        init()
        setViewPager()
        supportActionBar?.elevation = 0f
    }
    private fun init(){
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
        } else {
            binding.progressBar.visibility = View.GONE
            binding.layoutDetail.visibility = View.VISIBLE
            binding.layoutFollow.visibility = View.VISIBLE
        }
    }
}