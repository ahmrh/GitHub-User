package com.ahmrh.githubuser.ui.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmrh.githubuser.api.ApiConfig
import com.ahmrh.githubuser.api.UserItem
import com.ahmrh.githubuser.api.UserResponse
import com.ahmrh.githubuser.database.FavoriteUser
import com.ahmrh.githubuser.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : ViewModel() {

    companion object {
        private const val TAG = "UserViewModel"
    }

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    private val _detail = MutableLiveData<UserResponse>()
    val detail: LiveData<UserResponse> = _detail

    private val _listFollower = MutableLiveData<List<UserItem>>()
    val listFollower: LiveData<List<UserItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<UserItem>>()
    val listFollowing: LiveData<List<UserItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    // Favorite User Method
    fun isFavorite(username: String) = mFavoriteUserRepository.getFavoriteUserByUsername(username)
    fun delete(user: FavoriteUser){
        mFavoriteUserRepository.delete(user)
    }
    fun insert(user: FavoriteUser) {
        mFavoriteUserRepository.insert(user)
    }

    // User Method
    fun getDetail(username: String) {

        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detail.value = response.body()
                } else {
                    Log.e(TAG, "onFailureResponseDetail: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureThrowableDetail: ${t.message}")
            }
        })
    }


    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailureResponseFollowing: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureThrowableFollowing: ${t.message}")
            }
        })
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailureResponseFollower: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureThrowableFollower: ${t.message}")
            }
        })
    }
}