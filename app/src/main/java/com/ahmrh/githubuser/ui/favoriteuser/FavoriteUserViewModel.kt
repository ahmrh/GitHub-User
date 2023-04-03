package com.ahmrh.githubuser.ui.favoriteuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmrh.githubuser.api.UserItem
import com.ahmrh.githubuser.database.FavoriteUser
import com.ahmrh.githubuser.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFavoriteUser = MutableLiveData<List<FavoriteUser>>()
    val listFavoriteUser = _listFavoriteUser

    fun insert(user: FavoriteUser) {
        mFavoriteUserRepository.insert(user)
    }

    fun delete(user: FavoriteUser) {
        mFavoriteUserRepository.delete(user)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>? =
        mFavoriteUserRepository.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        mFavoriteUserRepository.getFavoriteUserByUsername(username)



}