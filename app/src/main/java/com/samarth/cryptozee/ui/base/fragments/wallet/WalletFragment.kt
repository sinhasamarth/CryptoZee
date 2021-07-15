package com.samarth.cryptozee.ui.base.fragments.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.WalletFragmentBinding

private lateinit var binding: WalletFragmentBinding
class FavouriteFragment : Fragment(R.layout.wallet_fragment){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WalletFragmentBinding.inflate(layoutInflater)
    }
}