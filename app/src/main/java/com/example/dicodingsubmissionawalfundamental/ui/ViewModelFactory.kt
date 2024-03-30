package com.example.dicodingsubmissionawalfundamental.ui

import androidx.lifecycle.ViewModelProvider
import com.example.dicodingsubmissionawalfundamental.data.FavoriteRepository

class ViewModelFactory private constructor(private val favoriteRepository: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(favoriteRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//    }
//
//    companion object {
//        @Volatile
//        private var instance: ViewModelFactory? = null
//        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
//            instance ?: ViewModelFactory(Injection.provideRepository(context))
//        }.also { instance = it }
//    }
}