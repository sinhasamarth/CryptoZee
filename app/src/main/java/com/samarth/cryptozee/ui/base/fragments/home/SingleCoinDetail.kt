package com.samarth.cryptozee.ui.base.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.HomeFragmentBinding
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding

private lateinit var binding : SingleCoinDetailFragmentBinding
class SingleCoinDetail :Fragment(R.layout.single_coin_detail_fragment){
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }
}