package com.samarth.cryptozee.data.model.localStorage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.entities.WalletDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDetailDao {
    @Insert
    suspend fun addWalletDetail(walletDetailsEntity: WalletDetailsEntity)

    @Query("DELETE from wallet_details")
    suspend fun deleteWalletDB()

    @Query("SELECT *  FROM wallet_details WHERE id = 1")
    suspend fun getWalletDetail(): WalletDetailsEntity

    @Update
    suspend fun updateWallet(walletDetailsEntity: WalletDetailsEntity)
}