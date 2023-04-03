package com.ahmrh.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ahmrh.githubuser.database.FavoriteUser
import com.ahmrh.githubuser.database.FavoriteUserDao
import com.ahmrh.githubuser.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>? {
        var listFavoriteUsers: LiveData<List<FavoriteUser>>? = null
        executorService.execute {
            mFavoriteUserDao.getAllFavoriteUser()
        }
        return listFavoriteUsers
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        mFavoriteUserDao.getFavoriteUserByUsername(username)

    fun deleteFavoriteUserByUsername(username: String) {
        executorService.execute{
            mFavoriteUserDao.deleteFavoriteUserByUsername(username)
        }
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }

    fun update(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.update(favoriteUser) }
    }

}