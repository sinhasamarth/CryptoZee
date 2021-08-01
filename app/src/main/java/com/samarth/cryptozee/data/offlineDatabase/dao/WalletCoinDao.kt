package com.samarth.cryptozee.data.offlineDatabase.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.WalletCoinEntity

@Dao
interface WalletCoinDao {

    // Inserting New Coin
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoinToWallet( walletCoinEntity: WalletCoinEntity)

    //Removing the Coin
    @Delete
    suspend fun removeCoin(walletCoinEntity: WalletCoinEntity)

    // Getting if Coin is Present
    @Query("SELECT * FROM  wallet_Coins WHERE coin_Id= :coinId")
    suspend fun getCoinDetail(coinId:String): WalletCoinEntity

    // Getting All the Wallet Coins
    @Query("SELECT * FROM wallet_Coins ORDER BY quantity DESC")
    fun walletCoin():List<WalletCoinEntity>

    // Updating the Coin Value
    @Query("UPDATE wallet_Coins SET quantity  = :quantity WHERE coin_Id = :coinId ")
    suspend fun updateCoin(quantity:Double , coinId :String)

}