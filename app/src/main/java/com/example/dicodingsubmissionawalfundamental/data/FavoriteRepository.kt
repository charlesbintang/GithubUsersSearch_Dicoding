package com.example.dicodingsubmissionawalfundamental.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.dicodingsubmissionawalfundamental.data.local.entity.FavoriteUserEntity
import com.example.dicodingsubmissionawalfundamental.data.local.room.FavoriteDao
import com.example.dicodingsubmissionawalfundamental.data.remote.response.DetailUserResponse
import com.example.dicodingsubmissionawalfundamental.data.remote.response.ItemsItem
import com.example.dicodingsubmissionawalfundamental.data.remote.retrofit.ApiService

class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
) {
    private val _listUser = MutableLiveData<List<ItemsItem?>?>()
    val listUser: LiveData<List<ItemsItem?>?> = _listUser
    private val _detailUserResponse = MutableLiveData<DetailUserResponse>()
    val detailUserResponse: LiveData<DetailUserResponse> = _detailUserResponse

    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>> {
        return favoriteDao.getAllFavoriteUser()
    }

    suspend fun setFavoriteUser(favorite: FavoriteUserEntity) {
        return favoriteDao.insert(favorite)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity> {
        return favoriteDao.getFavoriteUserByUsername(username)
    }

    suspend fun deleteFavoriteUser(favorite: FavoriteUserEntity) {
        return favoriteDao.delete(favorite)
    }

    fun findUser(q: String): LiveData<Result<List<ItemsItem?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(q)
            val user = response.items
            emit(Result.Success(user))
            _listUser.value = user
        } catch (e: Exception) {
            Log.d(TAG, "findUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun findDetailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
            _detailUserResponse.value = response
        } catch (e: Exception) {
            Log.d(TAG, "findDetailUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun findFollowersUser(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "findFollowersUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun findFollowingUser(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "findFollowingUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        private const val TAG = "FavoriteRepository"

        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
        ): FavoriteRepository = instance ?: synchronized(this) {
            instance ?: FavoriteRepository(apiService, favoriteDao)
        }.also { instance = it }


    }
}