package com.samarth.cryptozee.ui.base.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding
import com.samarth.cryptozee.ui.adapters.HomeRecylerViewAdapter
import com.samarth.cryptozee.ui.base.viewmodel.MainViewModel
import com.samarth.cryptozee.ui.dataFormatter.SetSingleCoinData
import com.samarth.cryptozee.utils.CONSTANTS

private  var binding: SingleCoinDetailFragmentBinding ? = null

class SingleCoinDetail : Fragment() {
    val viewModelShared:MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)
        Refreshit()
        return binding!!.root
    }

    fun Refreshit(){
        val viewModel = MainViewModel()
        viewModel.getSingleCoinDetail(viewModelShared.marketCoinResponse?.id.toString())
        viewModel.singleCoinResponse.observe(viewLifecycleOwner, { response ->
            SetSingleCoinData(response, binding!! , viewModelShared.marketCoinResponse!!)
        })
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }
}