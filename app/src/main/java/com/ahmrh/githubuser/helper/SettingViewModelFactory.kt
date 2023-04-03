package com.ahmrh.githubuser.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmrh.githubuser.ui.setting.SettingPreferences
import com.ahmrh.githubuser.ui.setting.SettingViewModel

class SettingViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown SettingViewModel class: " + modelClass.name)
    }
}