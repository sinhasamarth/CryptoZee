package com.samarth.cryptozee.ui.base.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.HomeFragmentBinding

private lateinit var binding:HomeFragmentBinding
class HomeFragment: Fragment(R.layout.home_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        super.onViewCreated(view, savedInstanceState)

    }
}