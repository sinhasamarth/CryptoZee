package com.samarth.cryptozee.data.offlineDatabase.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.TransactionEntity

@Dao
interface TransactionDao {

    // Inserting into DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToTransaction(transactionEntity: TransactionEntity)

    //GETTING FROM DB
    @Query("SELECT * FROM transaction_Table ORDER BY date DESC")
    fun getAllTransaction(): List<TransactionEntity>

    //DELETING ALL THE TRANSACTION
    @Query("DELETE FROM transaction_table")
    suspend fun deleteAllTransaction()
}