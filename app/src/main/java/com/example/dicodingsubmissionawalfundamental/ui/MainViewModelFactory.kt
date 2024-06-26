package com.example.dicodingsubmissionawalfundamental.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingsubmissionawalfundamental.data.FavoriteRepository
import com.example.dicodingsubmissionawalfundamental.di.Injection
import com.example.dicodingsubmissionawalfundamental.helper.SettingPreferences
import com.example.dicodingsubmissionawalfundamental.helper.dataStore

class MainViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository,
    private val pref: SettingPreferences
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                favoriteRepository,
                pref
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: MainViewModelFactory? = null
        fun getInstance(context: Context): MainViewModelFactory = instance ?: synchronized(this) {
            instance ?: MainViewModelFactory(
                Injection.provideRepository(context),
                SettingPreferences.getInstance(context.dataStore)
            )
        }.also { instance = it }
    }
}