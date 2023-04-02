package com.ahmrh.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.ahmrh.githubuser.database.FavoriteUser

class FavoriteUserDiffCallback(
    private val mOldFavoriteUserList: List<FavoriteUser>,
    private val mNewFavoriteUserList: List<FavoriteUser>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldFavoriteUserList.size

    override fun getNewListSize(): Int = mNewFavoriteUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldFavoriteUserList[oldItemPosition].username == mNewFavoriteUserList[newItemPosition].username

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = mOldFavoriteUserList[oldItemPosition]
        val newFavoriteUser = mOldFavoriteUserList[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username && oldFavoriteUser.avatarUrl == newFavoriteUser.avatarUrl
    }
}