package com.example.dicodingsubmissionawalfundamental.data

import androidx.lifecycle.LiveData
import com.example.dicodingsubmissionawalfundamental.data.local.entity.FavoriteUserEntity
import com.example.dicodingsubmissionawalfundamental.data.local.room.FavoriteDao
import com.example.dicodingsubmissionawalfundamental.data.remote.retrofit.ApiService

class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
) {
    fun getAllFavoriteUser(): LiveData<FavoriteUserEntity> {
        return favoriteDao.getAllFavoriteUser()
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao
        ): FavoriteRepository = instance ?: synchronized(this) {
            instance ?: FavoriteRepository(apiService, favoriteDao)
        }.also { instance = it }
    }
}