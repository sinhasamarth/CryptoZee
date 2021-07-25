package com.samarth.cryptozee.data.model.localStorage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samarth.cryptozee.data.model.localStorage.dao.FavouriteDao
import com.samarth.cryptozee.data.model.localStorage.dao.TransactionDao
import com.samarth.cryptozee.data.model.localStorage.dao.WalletCoinDao
import com.samarth.cryptozee.data.model.localStorage.dao.WalletInfoDao
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.data.model.localStorage.entities.TransactionEntity
import com.samarth.cryptozee.data.model.localStorage.entities.WalletCoinEntity
import com.samarth.cryptozee.data.model.localStorage.entities.WalletInfoEntity

@Database(entities = [FavouriteEntity::class, TransactionEntity::class , WalletInfoEntity::class, WalletCoinEntity::class ], version = 4, exportSchema = false)
abstract class LocalStorageDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao
    abstract fun transactionDao(): TransactionDao
    abstract fun walletInfoDao(): WalletInfoDao
    abstract fun walletCoinDao():WalletCoinDao
    //Apply Singleton Class
    companion object {
        @Volatile
        private var INSTANCE: LocalStorageDatabase? = null

        fun getDatabase(context: Context): LocalStorageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalStorageDatabase::class.java,
                        "database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}