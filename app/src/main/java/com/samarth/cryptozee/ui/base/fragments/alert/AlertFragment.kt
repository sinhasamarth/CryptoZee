package com.samarth.cryptozee.ui.base.fragments.alert

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.AlertFragmentBinding
import com.samarth.cryptozee.service.ForegroundService
import com.samarth.cryptozee.ui.adapters.FavouriteHomeAdapter
import com.samarth.cryptozee.ui.listeners.SingleCoinItemClickListeners
import com.samarth.cryptozee.viewModelShared

private lateinit var binding:AlertFragmentBinding
class AlertFragment : Fragment(),SingleCoinItemClickListeners{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlertFragmentBinding.inflate(layoutInflater)
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.recylerViewAlert.layoutManager = LinearLayoutManager(context)
            viewModelShared.allAlertCoin.observe(viewLifecycleOwner,{data->
                binding.recylerViewAlert.adapter = FavouriteHomeAdapter(listAlertEntity = data , itemClickListnersAlert = this)
            })
    }

    override fun onItemClick(position: Int) {
        val coinId = viewModelShared.allAlertCoin.value!![position].coinId
        viewModelShared.coinIDForSharing = coinId
        findNavController().navigate(R.id.action_alertFragment_to_singleCoinDetail)
    }
}