package com.ahmrh.githubuser.ui.user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ahmrh.githubuser.database.FavoriteUser
import com.ahmrh.githubuser.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun insert(user: FavoriteUser) {
        mFavoriteUserRepository.insert(user)
    }

    fun delete(user: FavoriteUser) {
        mFavoriteUserRepository.delete(user)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> =
        mFavoriteUserRepository.getAllFavoriteUsers()


}