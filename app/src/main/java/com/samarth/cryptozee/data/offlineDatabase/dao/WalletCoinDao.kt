package com.samarth.cryptozee.data.offlineDatabase.database.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.WalletCoinEntity

@Dao
interface WalletCoinDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCoinToWallet( walletCoinEntity: WalletCoinEntity)

    @Delete
    suspend fun removeCoin(walletCoinEntity: WalletCoinEntity)

    @Query("SELECT * FROM wallet_Coins WHERE coin_Id= :coinId")
    suspend fun getCoinDetail(coinId:String): WalletCoinEntity

    @Query("SELECT * FROM wallet_Coins ORDER BY quantity DESC")
    suspend fun walletCoin():List<WalletCoinEntity>

}