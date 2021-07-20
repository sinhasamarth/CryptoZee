package com.samarth.cryptozee.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.localStorage.entities.AlertEntity
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.ui.base.fragments.alert.AlertFragment
import com.samarth.cryptozee.ui.base.fragments.favourite.FavouriteFragment
import com.samarth.cryptozee.ui.dataFormatter.DataFormat
import com.samarth.cryptozee.ui.listeners.SingleCoinItemClickListeners

class FavouriteHomeAdapter(
    private val listFavouriteEntity: List<FavouriteEntity>? = null,
    private val itemClickListnersFavourite: FavouriteFragment? = null,
    private val listAlertEntity: List<AlertEntity>? = null,
    private val itemClickListnersAlert: AlertFragment? = null
) :
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
        val dataForSet:FavouriteEntity = if (listFavouriteEntity != null ){
            listFavouriteEntity[position]
        } else{
            val temp = listAlertEntity!!.get(position)
            FavouriteEntity(temp.coinId,temp.coinName,temp.price,temp.coin_Image_Link,temp.coin_Change_In_24H)
        }
        //Setting Image
        Glide.with(holder.itemView.context)
            .load(dataForSet.coin_Image_Link)
            .placeholder(R.drawable.ic_icons8_loading)
            .into(holder.imageOfCoin)

        //Setting Name of Coin
        holder.nameOfCoin.text = DataFormat.formatName(dataForSet.coinName!!)

        //Setting Price of Coin
        holder.priceOfCoin.text = DataFormat.formatPrice(dataForSet.price.toString())

        // Getting Formatted Data  of Change in 24 Hours
        DataFormat.getChangeFormatted(
            dataForSet.coin_Change_In_24H.toString(),
            holder.changeIn24Hours
        )

        // Handling On Click On itemView
        holder.itemView.setOnClickListener {
            if (itemClickListnersFavourite != null)
                itemClickListnersFavourite.onItemClick(position)
            else
                itemClickListnersAlert!!.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listFavouriteEntity?.size ?: listAlertEntity!!.size

    }
}