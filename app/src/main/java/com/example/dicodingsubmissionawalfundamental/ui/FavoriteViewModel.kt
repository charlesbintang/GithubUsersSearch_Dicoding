package com.example.dicodingsubmissionawalfundamental.ui

import androidx.lifecycle.ViewModel
import com.example.dicodingsubmissionawalfundamental.data.FavoriteRepository

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    fun getAllFavoriteUser() = favoriteRepository.getAllFavoriteUser()
}