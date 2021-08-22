package com.samarth.cryptozee.ui.dataFormatter

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.localStorage.WalletInfoEntity
import com.samarth.cryptozee.databinding.WalletFragmentBinding
import com.samarth.cryptozee.viewModelShared
import java.text.DecimalFormat

object SetWalletData {
    fun setWalletDeatils(binding: WalletFragmentBinding, walletInfoEntity: WalletInfoEntity) {
        binding.toolbar.title = "Welcome  ${walletInfoEntity.name}"
        val finalBalance =  getBalance(walletInfoEntity.usableMoney.toDouble())
        binding.currentBalance.text = getPriceFormatted(finalBalance)
        binding.percentofChange.text = getChangeInWallet(finalBalance)

    }

    private fun getChangeInWallet(finalBalance: String): String{
        val change = ((finalBalance.toDouble() - 10000.00)/100)
        val formattedData = DataFormat.formatQuantity(change).toDouble()
        return if(formattedData>=0){
            "+$formattedData%"
        } else{
            "$formattedData%"
        }
    }

    fun getBalance(usableMoney: Double):String{
        var balance = usableMoney
        viewModelShared.allWalletCoin.value!!.forEach { coinData ->
            viewModelShared.allCoinResponse.value!!.forEach { response ->
                if(coinData.coinId == response.id){
                    balance += coinData.quantity * response.currentPrice.toDouble()
                }
            }
        }

        return balance.toString()
    }
    fun getPriceFormatted(rawprice: String): String {
        return "$ " + DecimalFormat("##,##,###.##")
            .format(rawprice.toDouble())
            .toString()
    }

    fun pinkSnackBar(view: View,message:String, context: Context) {
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(ContextCompat.getColor(context, R.color.Pink)).setTextColor(
            Color.WHITE
        ).show()
    }

}