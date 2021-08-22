package com.samarth.cryptozee.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.localStorage.FavouriteEntity
import com.samarth.cryptozee.ui.base.fragments.favourite.FavouriteFragment
import com.samarth.cryptozee.ui.dataFormatter.DataFormat

class FavouriteHomeAdapter(private val listFavouriteEntity: List<FavouriteEntity>, val itemClickListners: FavouriteFragment) :
    RecyclerView.Adapter<FavouriteHomeAdapter.FavouriteViewHolder>() {

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageOfCoin = itemView.findViewById<ImageView>(R.id.CoinImage)
        val priceOfCoin = itemView.findViewById<TextView>(R.id.PriceOfCoin)
        val nameOfCoin = itemView.findViewById<TextView>(R.id.NameOfCoin)
        val changeIn24Hours = itemView.findViewById<TextView>(R.id.changeIn24hours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return (
                FavouriteViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.crypto_show_recylce_elements,
                        parent, false
                    )
                ))
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val dataForSet = listFavouriteEntity[position]
        //Setting Image
        Glide.with(holder.itemView.context)
            .load(dataForSet.coin_Image_Link)
            .placeholder(R.drawable.ic_icons8_loading)
            .into(holder.imageOfCoin)

        //Setting Name of Coin
        holder.nameOfCoin.text = DataFormat.formatName(dataForSet.coinName!!)

        //Setting Price of Coin
        holder.priceOfCoin.text = DataFormat.formatPrice(dataForSet.price!!)

        // Getting Formatted Data  of Change in 24 Hours
        DataFormat.getChangeFormatted(
            dataForSet.coin_Change_In_24H.toString(),
            holder.changeIn24Hours
        )

        // Handling On Click On itemView
        holder.itemView.setOnClickListener {
            itemClickListners.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = listFavouriteEntity.size
}