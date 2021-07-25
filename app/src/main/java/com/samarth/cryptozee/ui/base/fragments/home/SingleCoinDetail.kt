package com.samarth.cryptozee.ui.base.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding
import com.samarth.cryptozee.ui.dataFormatter.SetSingleCoinData
import com.samarth.cryptozee.viewModelShared
import com.samarth.cryptozee.MainActivity


private lateinit var binding: SingleCoinDetailFragmentBinding

class SingleCoinDetail : Fragment() {


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainActivity.startLoading()
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)
        binding.chart.clearCache(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val coinId = viewModelShared.coinIDForSharing!!
        Log.d("NAME", coinId)
        viewModelShared.getSingleCoinDetail(coinId)
        viewModelShared.singleCoinResponse.observe(viewLifecycleOwner, { response ->
            if (response.id == coinId) {
                Log.d("Response", response.toString())
                SetSingleCoinData.setAllTextDataToView(response, binding)
            }

        })

        viewModelShared.getCoinChart(coinId)
        viewModelShared.singleCoinChartResponse.observe(viewLifecycleOwner, { response ->
            SetSingleCoinData.setAllChartsToView(response, binding)
            if (!response.isNullOrEmpty()) {
                Log.d("RESPONSE", response.toString())
            }
            MainActivity.stopLoading()
            binding.loadingAnimationChart.visibility = View.VISIBLE
        })

        viewModelShared.allFavouriteCoin.value?.forEach {
            if (it.coinId == coinId) {
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.favtoggleButton.tag = "ON"
            }
        }


        //Favourite
        binding.favtoggleButton.setOnClickListener {
            val element = FavouriteEntity(
                viewModelShared.coinIDForSharing.toString(),
                binding.NameOfCoin.text.toString(),
                binding.PriceOfCoin.text.toString().replace("$", "").trim(),
                viewModelShared.coinForSharingImage,
                viewModelShared.coinForSharingChange.toString()
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
    }
}


