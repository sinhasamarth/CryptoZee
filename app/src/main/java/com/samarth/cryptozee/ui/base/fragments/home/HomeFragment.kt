package com.samarth.cryptozee.ui.base.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.samarth.cryptozee.MainActivity
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.databinding.HomeFragmentBinding
import com.samarth.cryptozee.ui.adapters.HomeRecylerViewAdapter
import com.samarth.cryptozee.ui.listeners.SingleCoinItemClickListeners
import com.samarth.cryptozee.viewModelShared


private lateinit var binding: HomeFragmentBinding

class HomeFragment : Fragment(), SingleCoinItemClickListeners {

    private var apiResponse: MarketCoinResponse = MarketCoinResponse()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = HomeFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        // Setting Nav Bar
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.title = "Market"
        setHasOptionsMenu(true)

        // Setting Layout
        binding.homeRecylerView.layoutManager = LinearLayoutManager(context)

        // Api Call For Coins
        viewModelShared.getAllCoin()

        viewModelShared.allCoinResponse.observe(viewLifecycleOwner) {
            it?.let {
                // Caching
                apiResponse = it
                // Setting Data in Recycler View
                binding.homeRecylerView.adapter = HomeRecylerViewAdapter(it, this)
                if(!it.isNullOrEmpty()){
                    MainActivity.stopLoading()
                }

            }
        }
        // Returning View
        return binding.root
    }

    // Setting Search Filters
    val searchResponse = apiResponse
    var queryText: String? = null
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.menu_search_home)

        val searchView = item?.actionView as SearchView
        binding.homeRecylerView.adapter = HomeRecylerViewAdapter(searchResponse, this)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                queryText = newText!!
                searchResponse.clear()
                binding.homeRecylerView.adapter =
                    HomeRecylerViewAdapter(searchResponse, this@HomeFragment)
                binding.homeRecylerView.adapter?.notifyDataSetChanged()
                val lowercaseAllQuery = newText.lowercase()
                if (lowercaseAllQuery.isNotEmpty()) {
                    apiResponse.forEach {
                        if (it.name.lowercase().contains(lowercaseAllQuery))
                            searchResponse.add(it)
                        else if ((it.symbol.lowercase().contains(lowercaseAllQuery)))
                            searchResponse.add(it)
                    }
                } else {
                    searchResponse.addAll(apiResponse)
                }
                binding.homeRecylerView.adapter?.notifyDataSetChanged()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClick(position: Int) {
        // Replacing Fragment
        if (queryText.isNullOrBlank())
            viewModelShared.coinIDForSharing = apiResponse[position].id
        else
            viewModelShared.coinIDForSharing = searchResponse[position].id
        findNavController().navigate(R.id.action_homeFragment_to_singleCoinDetail)

    }


}