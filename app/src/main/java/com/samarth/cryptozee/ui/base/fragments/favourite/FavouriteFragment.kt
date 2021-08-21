package com.samarth.cryptozee.ui.base.fragments.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        // Binding
        binding = FavouriteFragmentBinding.inflate(layoutInflater)

        // Setting Recycler View
        setRecyclerView()

        //Setting the toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarFav)
        (activity as AppCompatActivity).supportActionBar!!.title = "Favourite Coins";

        // Setting the Listeners
        binding.moveToHomeButton.setOnClickListener {
            findNavController().navigate(R.id.action_favouriteFragment_to_homeFragment)
        }
        // Returning View
        return binding.root
    }

    private fun setRecyclerView() {
        viewModelShared.getAllFavouriteCoin()
        binding.favouriteRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModelShared.allFavouriteCoin.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.nothingUi.visibility = View.VISIBLE
            } else {
                binding.nothingUi.visibility = View.INVISIBLE
            }
            binding.favouriteRecyclerView.adapter = FavouriteHomeAdapter(it, this)
        })
    }

    override fun onItemClick(position: Int) {
        val coinId = viewModelShared.allFavouriteCoin.value!![position].coinId
        viewModelShared.coinIDForSharing = coinId
        findNavController().navigate(R.id.action_favouriteFragment_to_singleCoinDetail)
    }

}
