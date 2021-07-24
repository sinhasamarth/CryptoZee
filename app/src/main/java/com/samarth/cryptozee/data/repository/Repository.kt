package com.samarth.cryptozee.data.repository

import androidx.lifecycle.LiveData
import com.samarth.cryptozee.data.api.AllChartApiRequest
import com.samarth.cryptozee.data.api.RetrofitInstance
import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.model.localStorage.dao.FavouriteDao
import com.samarth.cryptozee.data.model.localStorage.dao.TransactionDao
import com.samarth.cryptozee.data.model.localStorage.dao.WalletDetailDao
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.data.model.localStorage.entities.WalletDetailsEntity
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_PREFIX
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_SUFFIX
import com.samarth.cryptozee.viewModelShared
import kotlinx.coroutines.flow.Flow

class Repository(
    private val favouriteDao: FavouriteDao,
    private val walletDetailDao: WalletDetailDao,
    private val transationDao: TransactionDao
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

    fun getAllFavourite(): List<FavouriteEntity> {
        return favouriteDao.getAllFavourite()
    }

    // Wallet

    //GetDetail

    suspend fun getDetail(): WalletDetailsEntity {
        return walletDetailDao.getWalletDetail()
    }

    //Insert To Wallet

    suspend fun addDetailToWallet(walletDetailsEntity: WalletDetailsEntity) {
        walletDetailDao.addWalletDetail(walletDetailsEntity)
    }

    // Update

    suspend fun updateWallet(walletDetailsEntity: WalletDetailsEntity) {
        walletDetailDao.updateWallet(walletDetailsEntity)
    }

}