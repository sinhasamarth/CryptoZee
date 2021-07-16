package com.samarth.cryptozee.ui.base.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding

private lateinit var binding: SingleCoinDetailFragmentBinding

class SingleCoinDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }




}