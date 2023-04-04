package com.ahmrh.githubuser.ui.favoriteuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmrh.githubuser.database.FavoriteUser
import com.ahmrh.githubuser.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        _isLoading.value = true
        _isLoading.value = false
        return mFavoriteUserRepository.getAllFavoriteUsers()
    }




}