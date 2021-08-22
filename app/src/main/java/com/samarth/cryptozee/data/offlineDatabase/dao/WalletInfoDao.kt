package com.samarth.cryptozee.data.offlineDatabase.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.WalletInfoEntity

@Dao
interface WalletInfoDao {
    @Insert
    suspend fun addWalletDetail(walletInfoEntity: WalletInfoEntity)

    @Query("DELETE from wallet_info")
    suspend fun deleteWalletDB()

    @Query("SELECT *  FROM wallet_info WHERE id = 1")
    suspend fun getWalletDetail(): WalletInfoEntity

    @Query("UPDATE wallet_info SET Usable_Money = :usableMoney WHERE id = 1")
    suspend fun updateWallet(usableMoney:String)
}