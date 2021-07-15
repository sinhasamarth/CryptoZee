package com.samarth.cryptozee.ui.base.fragments.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.FavouriteFragmentBinding

private lateinit var binding: FavouriteFragmentBinding
class FavouriteFragment : Fragment(R.layout.alert_fragment){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FavouriteFragmentBinding.inflate(layoutInflater)
    }
}