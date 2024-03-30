package com.example.dicodingsubmissionawalfundamental.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,
)
