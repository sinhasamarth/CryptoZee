package com.samarth.cryptozee.ui.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.MarketCoinResponse
import com.samarth.cryptozee.ui.dataFormatter.TextFormat
import com.samarth.cryptozee.utils.CONSTANTS.Companion.LOG_TAG

class MainHomeRecylerView(val marketCoinResponse: MarketCoinResponse) :
    RecyclerView.Adapter<MainHomeRecylerView.MainHomeViewHolder>() {
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
        holder.nameOfCoin.text = TextFormat.formatName(dataForSet.name)

        //Setting Price of Coin
        Log.d(LOG_TAG,dataForSet.currentPrice.toString())
        holder.priceOfCoin.text = TextFormat.formatPrice(dataForSet.currentPrice.toString())

        // Getting Formatted Data  of Change in 24 Hours
        val formattedChange =
            TextFormat.getChangeFormatted(dataForSet.priceChangePercentage24h.toString())

        // Setting the Data of Change in 24 hours with Colours
        if (formattedChange < 0) {
            //Change Colour to Red
            holder.changeIn24Hours.setTextColor(Color.parseColor("#F24E4E"))
            holder.changeIn24Hours.text = "$formattedChange%"
        }
        else {
            //Change Colour to Green
            holder.changeIn24Hours.setTextColor(Color.parseColor("#A2D970"))
            holder.changeIn24Hours.text = "+$formattedChange%"

        }

        // Handling On Click On itemView
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = marketCoinResponse.size



}