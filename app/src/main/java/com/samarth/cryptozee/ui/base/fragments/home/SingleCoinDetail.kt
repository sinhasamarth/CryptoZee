package com.samarth.cryptozee.ui.base.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.data.model.localStorage.entities.WalletDetailsEntity
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding
import com.samarth.cryptozee.ui.dataFormatter.SetSingleCoinData
import com.samarth.cryptozee.viewModelShared

private lateinit var binding: SingleCoinDetailFragmentBinding

class SingleCoinDetail : Fragment() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)
        val coinId = viewModelShared.coinIDForSharing!!
        var coinDetailResponse: SingleCoinDetailResponse? = null
        viewModelShared.getSingleCoinDetail(coinId)
        viewModelShared.singleCoinResponse.observe(viewLifecycleOwner, { response ->
            SetSingleCoinData.setAllTextDataToView(
                response,
                binding,
            )
            coinDetailResponse = response
        })
        viewModelShared.getCoinChart(coinId)
        viewModelShared.singleCoinChartResponse.observe(viewLifecycleOwner, { response ->
            SetSingleCoinData.setAllChartsToView(response, binding, coinDetailResponse)
        })

        binding.favtoggleButton.setOnClickListener {
            val element = FavouriteEntity(
                coinDetailResponse!!.id,
                coinDetailResponse!!.name,
                coinDetailResponse!!.marketData.current_price.usd.toString(),
                coinDetailResponse!!.imageLink.url,
                coinDetailResponse!!.marketData.priceChangePercentage24h.toString()
            )

            if (binding.favtoggleButton.tag != "ON") {
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                viewModelShared.addToFavourites(element)
                binding.favtoggleButton.tag = "ON"
            } else {
                binding.favtoggleButton.tag = "OFF"
                viewModelShared.removeCoinFromFavourite(element)
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        //Buy Button
        //Getting The Usable Value
        var walletDetails: WalletDetailsEntity? = null;
        viewModelShared.getWalletDetails()
        viewModelShared.walletDetail.observe(viewLifecycleOwner, {
            walletDetails = it
        })

        //

        binding.buyButton.setOnClickListener {

        }
        return binding.root
    }


}