package com.samarth.cryptozee.data.offlineDatabase.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.FavouriteEntity

@Dao
interface FavouriteDao {

    // Getting All the Fav  Coins
    @Query("SELECT * FROM Favourite_Table ORDER BY coinId ASC ")
    fun getAllFavourite():List<FavouriteEntity>

    // Inserting the Coin
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addFavourite(data: FavouriteEntity)

    // Deleting the Coin
    @Delete
    suspend fun delFavourite(data: FavouriteEntity)
}