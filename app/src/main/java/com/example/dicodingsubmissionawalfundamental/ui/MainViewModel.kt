package com.example.dicodingsubmissionawalfundamental.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dicodingsubmissionawalfundamental.data.FavoriteRepository
import com.example.dicodingsubmissionawalfundamental.helper.SettingPreferences
import kotlinx.coroutines.launch

class MainViewModel(
    private val favoriteRepository: FavoriteRepository,
    private val pref: SettingPreferences,
) : ViewModel() {
    val listUser = favoriteRepository.listUser

    init {
        findUser(MainActivity.USER_ID)
    }

    fun findUser(q: String) = favoriteRepository.findUser(q)

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}