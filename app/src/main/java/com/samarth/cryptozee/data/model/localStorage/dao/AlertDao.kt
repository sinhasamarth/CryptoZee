package com.samarth.cryptozee.data.model.localStorage.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.entities.AlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToAlert(alertEntity: AlertEntity)
    @Delete
    suspend fun delAlert(alertEntity: AlertEntity)
    @Query("SELECT * FROM alert_Table ORDER BY coinId ASC")
    fun getAllAlertCoins(): Flow<List<AlertEntity>>
}