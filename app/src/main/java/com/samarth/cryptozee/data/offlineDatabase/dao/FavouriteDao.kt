package com.samarth.cryptozee.data.offlineDatabase.database.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.FavouriteEntity

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM Favourite_Table ORDER BY coinId ASC ")
    fun getAllFavourite():List<FavouriteEntity>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addFavourite(data: FavouriteEntity)

    @Delete
    suspend fun delFavourite(data: FavouriteEntity)
}