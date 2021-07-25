package com.samarth.cryptozee.data.model.localStorage.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.entities.WalletInfoEntity

@Dao
interface WalletInfoDao {
    @Insert
    suspend fun addWalletDetail(walletInfoEntity: WalletInfoEntity)

    @Query("DELETE from wallet_info")
    suspend fun deleteWalletDB()

    @Query("SELECT *  FROM wallet_info WHERE id = 1")
    suspend fun getWalletDetail(): WalletInfoEntity

    @Update
    suspend fun updateWallet(walletInfoEntity: WalletInfoEntity)
}