package com.samarth.cryptozee.data.model.localStorage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.samarth.cryptozee.data.model.localStorage.entities.WalletDetailsEntity

@Dao
interface WalletDetailDao {
    @Insert
    suspend fun addWalletDetail(walletDetailDao: WalletDetailDao)

    @Query("DELETE from wallet_details")
    suspend fun deleteWalletDB()

    @Query("SELECT * FROM WALLET_DETAILS WHERE id = 0 ")
    fun getWalletDetail(): WalletDetailsEntity

}