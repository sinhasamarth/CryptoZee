package com.samarth.cryptozee.ui.base.fragments.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.FavouriteFragmentBinding
import com.samarth.cryptozee.ui.adapters.FavouriteHomeAdapter
import com.samarth.cryptozee.ui.listeners.SingleCoinItemClickListeners
import com.samarth.cryptozee.viewModelShared

private lateinit var binding: FavouriteFragmentBinding

class FavouriteFragment : Fragment(), SingleCoinItemClickListeners {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavouriteFragmentBinding.inflate(layoutInflater)
        setRecyclerView()

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarFav)
        (activity as AppCompatActivity).supportActionBar!!.title = "Favourite Coins";
        return binding.root
    }

    private fun setRecyclerView() {
        viewModelShared.getAllFavouriteCoin()
        binding.favouriteRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModelShared.allFavouriteCoin.observe(viewLifecycleOwner, {
            binding.favouriteRecyclerView.adapter = FavouriteHomeAdapter(it,this)
        })
    }

    override fun onItemClick(position: Int) {
        val coinId = viewModelShared.allFavouriteCoin.value!![position].coinId
        viewModelShared.coinIDForSharing = coinId
        findNavController().navigate(R.id.action_favouriteFragment_to_singleCoinDetail)
    }
}
