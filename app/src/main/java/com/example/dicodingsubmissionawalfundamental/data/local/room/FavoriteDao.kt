package com.example.dicodingsubmissionawalfundamental.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dicodingsubmissionawalfundamental.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteUserEntity: FavoriteUserEntity)

    @Update
    suspend fun update(favoriteUserEntity: FavoriteUserEntity)

    @Delete
    suspend fun delete(favoriteUserEntity: FavoriteUserEntity)

    @Query("SELECT * from favoriteuserentity ORDER BY username ASC")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favoriteuserentity WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>

}