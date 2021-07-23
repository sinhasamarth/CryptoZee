package com.samarth.cryptozee.data.model.localStorage.dao

import androidx.room.*
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite_Table ORDER BY coinId ASC ")
    fun getAllFavourite():List<FavouriteEntity>

    @Insert(onConflict =  OnConflictStrategy.IGNORE ,)
    suspend fun addFavourite(data:FavouriteEntity)

    @Delete
    suspend fun delFavourite(data: FavouriteEntity)
}