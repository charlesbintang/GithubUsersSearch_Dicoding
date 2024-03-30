package com.example.dicodingsubmissionawalfundamental.ui

import androidx.lifecycle.ViewModel
import com.example.dicodingsubmissionawalfundamental.data.FavoriteRepository

class MainViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    val listUser = favoriteRepository.listUser

    init {
        findUser(MainActivity.USER_ID)
    }

//    fun findUser(q: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getUser(q)
//        client.enqueue(object : Callback<GithubResponse> {
//            override fun onResponse(
//                call: Call<GithubResponse>,
//                response: Response<GithubResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        _listUser.value = response.body()?.items
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    fun findUser(q: String) = favoriteRepository.findUser(q)

}