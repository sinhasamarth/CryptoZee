package com.samarth.cryptozee.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.localStorage.WalletCoinEntity
import com.samarth.cryptozee.ui.base.fragments.wallet.WalletFragment
import com.samarth.cryptozee.ui.dataFormatter.DataFormat
import com.samarth.cryptozee.viewModelShared

class WalletCoinRecyclerViewAdapter(
    private val walletCoinList: List<WalletCoinEntity>,
    private val totalElementToShow: Int = 5,
    private val walletFragment: WalletFragment
) : RecyclerView.Adapter<WalletCoinRecyclerViewAdapter.WalletCoinViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletCoinViewHolder {
        return WalletCoinViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.wallet_coin_recylcer_view,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: WalletCoinViewHolder, position: Int) {

        //Getting Data
        val coinData = walletCoinList[position]

        // Setting Data

        holder.quantityOfCoin.text = DataFormat.formatQuantity(coinData.quantity)
        holder.nameOfCoin.text = coinData.coinName
        holder.symbolOfCoin.text =
            " / " + coinData.coinSymbol[0].uppercase() + coinData.coinSymbol.substring(1)

        //Handling Clicks
        holder.itemView.setOnClickListener {
            viewModelShared.coinIDForSharing = coinData.coinId
            walletFragment.onItemClick(position)
        }

        viewModelShared.allCoinResponse.value!!.forEach {
            if (coinData.coinId == it.id) {
                holder.currentHolding.text =
                    DataFormat.formatPrice((it.currentPrice.toDouble() * coinData.quantity).toString(), true )
                DataFormat.getChangeFormatted(
                    it.priceChangePercentage24h.toString(),
                    holder.changeInCoin
                )
            }
        }
    }

    override fun getItemCount() = totalElementToShow

    inner class WalletCoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Getting Ui Elements

        val nameOfCoin: TextView = itemView.findViewById(R.id.NameOfCoin)
        val symbolOfCoin: TextView = itemView.findViewById(R.id.symbolOfCoin)
        val quantityOfCoin: TextView = itemView.findViewById(R.id.quantityCoins)
        val currentHolding: TextView = itemView.findViewById(R.id.currentHolding)
        val changeInCoin: TextView = itemView.findViewById(R.id.changeInCoin)

    }
}