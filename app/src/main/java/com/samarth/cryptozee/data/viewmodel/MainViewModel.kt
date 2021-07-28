package com.samarth.cryptozee.data.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.data.model.localStorage.entities.TransactionEntity
import com.samarth.cryptozee.data.model.localStorage.entities.WalletCoinEntity
import com.samarth.cryptozee.data.model.localStorage.entities.WalletInfoEntity
import com.samarth.cryptozee.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    //Calling On Object Create
    init {
        getAllFavouriteCoin()
    }
    //LiveData

    val allCoinResponse: MutableLiveData<MarketCoinResponse> = MutableLiveData<MarketCoinResponse>()
    val singleCoinResponse: MutableLiveData<SingleCoinDetailResponse> =
        MutableLiveData<SingleCoinDetailResponse>()
    val singleCoinChartResponse: MutableLiveData<ArrayList<SingleCoinChartResponse>> =
        MutableLiveData<ArrayList<SingleCoinChartResponse>>()
    val allFavouriteCoin: MutableLiveData<List<FavouriteEntity>> =
        MutableLiveData<List<FavouriteEntity>>()

    //Wallet Details
    val walletInfo: MutableLiveData<WalletInfoEntity> = MutableLiveData<WalletInfoEntity>()

    //Sharing Data Between Fragments
    var coinIDForSharing: String? = null

    fun getAllCoin() {
        viewModelScope.launch {
            try {
                allCoinResponse.value = repository.getAllCoin()
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }
    }

    fun getSingleCoinDetail(coinId: String) {
        viewModelScope.launch {
            try {
                singleCoinResponse.value = repository.singleCoinDetails(coinId)
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }

    }

    fun getCoinChart(coinId: String) {
        viewModelScope.launch {
            try {
                singleCoinChartResponse.value = repository.singleCoinChart(coinId)
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }
    }

    fun addToFavourites(favouriteEntity: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavourite(favouriteEntity)
        }
    }

    fun getAllFavouriteCoin() {
        viewModelScope.launch(Dispatchers.IO) {
            allFavouriteCoin.postValue(repository.getAllFavourite())
        }
    }

    fun removeCoinFromFavourite(entity: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delFavourite(entity)
        }
    }

    // Wallet

    fun createWallet(walletInfoEntity: WalletInfoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDetailToWallet(walletInfoEntity)
        }
    }

    fun getWalletInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            walletInfo.postValue(repository.getDetail())
        }
    }

    fun upDateWallet(usableMoney: String){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateWallet(usableMoney)
        }
    }

    //Add To Transaction

    fun addToTransaction(transactionEntity: TransactionEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(transactionEntity)
        }
    }

    //Add Coin to Wallet
    fun addCoinToWallet(coinEntity: WalletCoinEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCoinToWallet(coinEntity)
        }
    }

}
