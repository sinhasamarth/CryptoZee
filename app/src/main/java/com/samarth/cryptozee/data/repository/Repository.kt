package com.samarth.cryptozee.data.repository

import com.samarth.cryptozee.data.api.AllChartApiRequest
import com.samarth.cryptozee.data.api.RetrofitInstance
import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.offlineDatabase.dao.FavouriteDao
import com.samarth.cryptozee.data.offlineDatabase.dao.TransactionDao
import com.samarth.cryptozee.data.offlineDatabase.dao.WalletCoinDao
import com.samarth.cryptozee.data.offlineDatabase.dao.WalletInfoDao
import com.samarth.cryptozee.data.model.localStorage.FavouriteEntity
import com.samarth.cryptozee.data.model.localStorage.TransactionEntity
import com.samarth.cryptozee.data.model.localStorage.WalletCoinEntity
import com.samarth.cryptozee.data.model.localStorage.WalletInfoEntity
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_PREFIX
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_SUFFIX

class Repository(
    private val favouriteDao: FavouriteDao,
    private val walletInfoDao: WalletInfoDao,
    private val transactionDao: TransactionDao,
    private val walletCoinDao: WalletCoinDao
) {

    suspend fun getAllCoin(): MarketCoinResponse {
        return RetrofitInstance.responseAllApi.getAllCoins()
    }

    suspend fun singleCoinDetails(coinId: String): SingleCoinDetailResponse {
        return RetrofitInstance
            .responseAllApi
            .getSingleCoinDetails(
                SINGLE_COIN_URL_DETAIL_PREFIX + coinId.lowercase() + SINGLE_COIN_URL_DETAIL_SUFFIX
            )
    }

    suspend fun singleCoinChart(coinId: String): ArrayList<SingleCoinChartResponse> {
        return AllChartApiRequest.getAllCoinChart(coinId)
    }

    // Favourite Database Function

    suspend fun addFavourite(favouriteEntity: FavouriteEntity) {
        favouriteDao.addFavourite(favouriteEntity)
    }

    suspend fun delFavourite(favouriteEntity: FavouriteEntity) {
        favouriteDao.delFavourite(favouriteEntity)
    }

    // Getting all the Favourite Coin

    fun getAllFavourite(): List<FavouriteEntity> {
        return favouriteDao.getAllFavourite()
    }

    // Wallet

    //GetDetail

    suspend fun getDetail(): WalletInfoEntity {
        return walletInfoDao.getWalletDetail()
    }

    //Insert To Wallet

    suspend fun addDetailToWallet(walletInfoEntity: WalletInfoEntity) {
        walletInfoDao.addWalletDetail(walletInfoEntity)
    }

    // Update

    suspend fun updateWallet(usableMoney:String) {
        walletInfoDao.updateWallet(usableMoney)
    }

    //Transaction

    //Add to Transaction
    suspend fun addTransaction(transactionEntity: TransactionEntity){
        transactionDao.addToTransaction(transactionEntity)
    }

    // Get All transaction

    suspend fun getAllTransaction():List<TransactionEntity>{
        return transactionDao.getAllTransaction()
    }

    // Wallet

    //Add Coin To Wallet
    suspend fun addCoinToWallet(walletCoinEntity: WalletCoinEntity){
        walletCoinDao.addCoinToWallet(walletCoinEntity)
    }

    //Getting All the Coins
     fun getAllWalletCoins():List<WalletCoinEntity>{
        return walletCoinDao.walletCoin()
    }

    // Getting Single Coin Detail

    suspend fun getSingleCoinDetail(coinId: String):WalletCoinEntity{
        return  walletCoinDao.getCoinDetail(coinId)
    }

    // Updating the coin
    suspend fun updateWalletCoin(quantity:Double , coinId: String){
        walletCoinDao.updateCoin(quantity , coinId)
    }

    // Removing Coin From Wallet
    suspend fun removeCoinFromWallet(walletCoinEntity: WalletCoinEntity){
        walletCoinDao.removeCoin(walletCoinEntity)
    }

}