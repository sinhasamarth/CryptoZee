package com.samarth.cryptozee.data.model.localStorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_Coins")
data class WalletCoinEntity (
    @PrimaryKey
    @ColumnInfo(name = "coin_Id")
    val coinId:String,
    @ColumnInfo(name = "coin_name")
    val coinName:String ,
    @ColumnInfo(name = "coin_symbol")
    val coinSymbol:String,
    @ColumnInfo(name = "buying_Price")
    val buyingPrice:Double,
    @ColumnInfo(name = "quantity")
    val quantity:Double,
    @ColumnInfo(name="date")
    val date:String
)