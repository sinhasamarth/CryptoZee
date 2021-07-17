package com.samarth.cryptozee.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.MarketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.ui.dataFormatter.DataFormat
import com.samarth.cryptozee.ui.listeners.HomeRecylerViewListeners
import com.samarth.cryptozee.utils.CONSTANTS.Companion.LOG_TAG

class HomeRecylerViewAdapter(
    val marketCoinResponse: MarketCoinResponse,
    val homeRecylerViewListners: HomeRecylerViewListeners
) :
    RecyclerView.Adapter<HomeRecylerViewAdapter.MainHomeViewHolder>() {
    inner class MainHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageofCoin = itemView.findViewById<ImageView>(R.id.CoinImage)
        val priceOfCoin = itemView.findViewById<TextView>(R.id.PriceOfCoin)
        val nameOfCoin = itemView.findViewById<TextView>(R.id.NameOfCoin)
        val changeIn24Hours = itemView.findViewById<TextView>(R.id.changeIn24hours)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHomeViewHolder {
        return (
                MainHomeViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.crypto_show_recylce_elements,
                        parent, false
                    )
                ))
    }

    override fun onBindViewHolder(holder: MainHomeViewHolder, position: Int) {
        val dataForSet = marketCoinResponse[position]
        //Setting Image
        Glide.with(holder.itemView.context)
            .load(dataForSet.image)
            .placeholder(R.drawable.ic_icons8_loading)
            .into(holder.imageofCoin)

        //Setting Name of Coin
        holder.nameOfCoin.text = DataFormat.formatName(dataForSet.name)

        //Setting Price of Coin
//        Log.d(LOG_TAG, dataForSet.currentPrice.toString())
        holder.priceOfCoin.text = DataFormat.formatPrice(dataForSet.currentPrice)

        // Getting Formatted Data  of Change in 24 Hours
       DataFormat.getChangeFormatted(dataForSet.priceChangePercentage24h.toString() , holder.changeIn24Hours)

        // Handling On Click On itemView
        holder.itemView.setOnClickListener {
            homeRecylerViewListners.onItemClick(position)
        }
    }

    override fun getItemCount() = marketCoinResponse.size
}