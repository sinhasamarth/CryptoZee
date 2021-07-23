package com.samarth.cryptozee.data.model.localStorage.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.entities.TransactionEntity
//
//@Dao
//interface TransationDao {
//
//    // Inserting into DB
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addToTransaction(transactionEntity: TransactionEntity)
//
//    //GETTING FROM DB
//    @Query("SELECT * FROM transaction_Table ORDER BY date DESC")
//    fun getAllTransaction(): List<TransactionEntity>
//
//    //DELETING ALL THE TRANSACTION
//    @Query("DELETE FROM transaction_table")
//    suspend fun deleteAllTransaction()
//}