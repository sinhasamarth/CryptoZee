package com.samarth.cryptozee.data.model.localStorage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_Coins")
data class WalletCoinEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "coin_name")
    val coinName:String ,
    @ColumnInfo(name = "coin_Id")
    val coinId:String,
    @ColumnInfo(name = "buying_Price")
    val buyingPrice:String,
    @ColumnInfo(name = "quantity")
    val quantity:String,
    @ColumnInfo(name="date")
    val date:String
)