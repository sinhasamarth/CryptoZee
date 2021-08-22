package com.samarth.cryptozee.data.model.localStorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourite_Table")
data class FavouriteEntity(
        @PrimaryKey
        val coinId: String,
        @ColumnInfo(name = "coin_Name")
        val coinName: String?,
        @ColumnInfo(name = "coin_Price")
        val price: String?,
        @ColumnInfo(name = "coin_Image_Link")
        val coin_Image_Link: String?,
        @ColumnInfo(name = "coin_Change_In_24H")
        val coin_Change_In_24H: String?

)
