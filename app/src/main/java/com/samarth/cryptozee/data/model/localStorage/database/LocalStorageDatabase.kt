package com.samarth.cryptozee.data.model.localStorage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samarth.cryptozee.data.model.localStorage.dao.FavouriteDao
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity

@Database(entities = [FavouriteEntity::class], version = 2, exportSchema = false)
abstract class LocalStorageDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao
    //Apply Singleton Class
    companion object {
        @Volatile
        private var INSTANCE: LocalStorageDatabase? = null

        fun getDatabase(context: Context): LocalStorageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalStorageDatabase::class.java,
                        "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}