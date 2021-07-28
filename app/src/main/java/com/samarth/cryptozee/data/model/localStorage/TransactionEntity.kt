package com.samarth.cryptozee.data.model.localStorage


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_Table")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "coin_Name")
    val coinName: String,
    @ColumnInfo(name = "quantity")
    val numberOfCoins: Double,
    @ColumnInfo(name = "date")
    val dateOfTransaction: String,
    @ColumnInfo(name = "coin_Id")
    val coinId: String,
    @ColumnInfo(name = "coin_Symbol")
    val coinSymbol: String,
    @ColumnInfo(name = "Buy/Sell")
    val buy_sell: Boolean,
    @ColumnInfo(name = "total_Price")
    val totalPrice: Double
)

