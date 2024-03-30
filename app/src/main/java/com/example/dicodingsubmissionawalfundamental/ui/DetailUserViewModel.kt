package com.example.dicodingsubmissionawalfundamental.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingsubmissionawalfundamental.data.FavoriteRepository
import com.example.dicodingsubmissionawalfundamental.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.launch

class DetailUserViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
//    private val _listUser = MutableLiveData<List<ItemsItem?>?>()
//    val listUser: LiveData<List<ItemsItem?>?> = _listUser

    val detailUserResponse = favoriteRepository.detailUserResponse
    val listUser = favoriteRepository.listUser


//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading

//    fun findDetailUser(username: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getDetailUser(username)
//        client.enqueue(object : Callback<DetailUserResponse> {
//            override fun onResponse(
//                call: Call<DetailUserResponse>,
//                response: Response<DetailUserResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        _detailUserResponse.value = response.body()
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//
//        })
//    }

    fun findDetailUser(username: String) = favoriteRepository.findDetailUser(username)

//    fun findFollowersUser(username: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getFollowers(username)
//        client.enqueue(object : Callback<List<ItemsItem>> {
//            override fun onResponse(
//                call: Call<List<ItemsItem>>,
//                response: Response<List<ItemsItem>>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        _listUser.value = responseBody
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//
//        })
//    }

    fun findFollowersUser(username: String) = favoriteRepository.findFollowersUser(username)

//    fun findFollowingUser(username: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getFollowing(username)
//        client.enqueue(object : Callback<List<ItemsItem>> {
//            override fun onResponse(
//                call: Call<List<ItemsItem>>,
//                response: Response<List<ItemsItem>>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        _listUser.value = responseBody
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//
//        })
//    }

    fun findFollowingUser(username: String) = favoriteRepository.findFollowingUser(username)

    fun setFavoriteUser(username: String, avatarUrl: String?) {
        viewModelScope.launch {
            val user = FavoriteUserEntity(
                username,
                avatarUrl
            )
            favoriteRepository.setFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(username: String, avatarUrl: String?) {
        viewModelScope.launch {
            val user = FavoriteUserEntity(
                username, avatarUrl
            )
            favoriteRepository.deleteFavoriteUser(user)
        }
    }

    fun getFavoriteUserByUsername(username: String) =
        favoriteRepository.getFavoriteUserByUsername(username)
}