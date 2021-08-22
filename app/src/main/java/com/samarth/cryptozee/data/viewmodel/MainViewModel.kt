package com.samarth.cryptozee.data.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.model.localStorage.FavouriteEntity
import com.samarth.cryptozee.data.model.localStorage.TransactionEntity
import com.samarth.cryptozee.data.model.localStorage.WalletCoinEntity
import com.samarth.cryptozee.data.model.localStorage.WalletInfoEntity
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
    val allWalletCoin: MutableLiveData<List<WalletCoinEntity>> =
        MutableLiveData<List<WalletCoinEntity>>()

    //Sharing Data Between Fragments
    var coinIDForSharing: String? = null

    // Getting  Wallet Coin Details
    val walletSingleCoin by lazy {
        MutableLiveData<WalletCoinEntity>()
    }


    // All  Market Coin Api
    fun getAllCoin() {
        viewModelScope.launch {
            try {
                allCoinResponse.value = repository.getAllCoin()
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }
    }

    // Single Coin Detail Api Call
    fun getSingleCoinDetail(coinId: String) {
        viewModelScope.launch {
            try {
                singleCoinResponse.value = repository.singleCoinDetails(coinId)
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }

    }

    // Single Coin Chart Api Call

    fun getCoinChart(coinId: String) {
        viewModelScope.launch {
            try {
                singleCoinChartResponse.postValue(repository.singleCoinChart(coinId))
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }
    }

    // Adding to Fav
    fun addToFavourites(favouriteEntity: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavourite(favouriteEntity)
        }
    }

    // Getting All Fav Coins
    fun getAllFavouriteCoin() {
        viewModelScope.launch(Dispatchers.IO) {
            allFavouriteCoin.postValue(repository.getAllFavourite())
        }
    }

    // Removing From Fav
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

    fun upDateWallet(usableMoney: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWallet(usableMoney)
        }
    }

    fun addWalletSingleCoin(walletCoinEntity: WalletCoinEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            walletSingleCoin.postValue(repository.getSingleCoinDetail(walletCoinEntity.coinId))
        }
        if (walletSingleCoin.value == null) {
            addCoinToWallet(walletCoinEntity)
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateWalletCoin(
                    walletCoinEntity.quantity + walletSingleCoin.value?.quantity!!,
                    walletCoinEntity.coinId
                )
            }
        }
    }

    // Get Single Coin Details

    fun getSingleCoin(coinId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            walletSingleCoin.postValue(repository.getSingleCoinDetail(coinId))
        }
    }
    //Add To Transaction

    fun addToTransaction(transactionEntity: TransactionEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(transactionEntity)
        }
    }

    //Add Coin to Wallet
    fun addCoinToWallet(coinEntity: WalletCoinEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCoinToWallet(coinEntity)
        }
    }

    //Getting Wallet Details
    fun getAllWalletCoin() {
        viewModelScope.launch(Dispatchers.IO) {
            allWalletCoin.postValue(repository.getAllWalletCoins())
        }
    }

    fun updateCoinQuantity(quantity: Double, walletCoinEntity: WalletCoinEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (quantity <= 0.00001 || quantity == 0.00) {
                repository.removeCoinFromWallet(walletCoinEntity)
            }
            else {
                repository.updateWalletCoin(quantity, walletCoinEntity.coinId)
            }

        }
    }

}
