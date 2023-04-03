package com.ahmrh.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser : FavoriteUser)

    @Update
    fun update(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from favoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * from favoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("DELETE from favoriteUser WHERE username = :username")
    fun deleteFavoriteUserByUsername(username: String)


}