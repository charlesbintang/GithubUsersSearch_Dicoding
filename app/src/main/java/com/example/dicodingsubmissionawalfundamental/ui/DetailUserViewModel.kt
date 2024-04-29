package com.example.dicodingsubmissionawalfundamental.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingsubmissionawalfundamental.data.FavoriteRepository
import com.example.dicodingsubmissionawalfundamental.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.launch

class DetailUserViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    val detailUserResponse = favoriteRepository.detailUserResponse
    val listUser = favoriteRepository.listUser

    fun findDetailUser(username: String) = favoriteRepository.findDetailUser(username)

    fun findFollowersUser(username: String) = favoriteRepository.findFollowersUser(username)

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