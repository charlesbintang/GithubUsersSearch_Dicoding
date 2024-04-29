package com.example.dicodingsubmissionawalfundamental.di

import android.content.Context
import com.example.dicodingsubmissionawalfundamental.data.FavoriteRepository
import com.example.dicodingsubmissionawalfundamental.data.local.room.FavoriteDatabase
import com.example.dicodingsubmissionawalfundamental.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return FavoriteRepository.getInstance(apiService, dao)
    }
}