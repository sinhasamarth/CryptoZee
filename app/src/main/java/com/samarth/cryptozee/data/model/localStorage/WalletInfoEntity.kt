package com.samarth.cryptozee.data.model.localStorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Wallet_Info")
data class WalletInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "owner_name")
    val name:String,
    @ColumnInfo(name = "Total_Portfolio_Value")
    val totalPortfolio:String,
    @ColumnInfo(name = "Usable_Money")
    var usableMoney:String,
    @ColumnInfo(name = "wallet_Created_Day")
    val creationDate:String
)