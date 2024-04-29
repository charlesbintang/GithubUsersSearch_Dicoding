package com.example.dicodingsubmissionawalfundamental.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dicodingsubmissionawalfundamental.data.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java, "Favorite.db"
                ).build()
            }
    }
}