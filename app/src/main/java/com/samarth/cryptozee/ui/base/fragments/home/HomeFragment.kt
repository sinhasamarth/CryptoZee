package com.samarth.cryptozee.ui.base.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.MarketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.databinding.HomeFragmentBinding
import com.samarth.cryptozee.ui.adapters.HomeRecylerViewAdapter
import com.samarth.cryptozee.ui.base.viewmodel.MainViewModel
import com.samarth.cryptozee.ui.listeners.HomeRecylerViewListeners
import com.samarth.cryptozee.utils.CONSTANTS.Companion.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private lateinit var binding: HomeFragmentBinding

class HomeFragment : Fragment(), HomeRecylerViewListeners {
    val viewModel: MainViewModel  by activityViewModels()
    private var apiResponse: MarketCoinResponse = MarketCoinResponse()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        binding.homeRecylerView.layoutManager = LinearLayoutManager(context)

        viewModel.getAllCoin()
        viewModel.allCoinResponse.observe(viewLifecycleOwner, {
            it?.let {
                Log.d(LOG_TAG, it.toString())
                apiResponse = it
                binding.homeRecylerView.adapter = HomeRecylerViewAdapter(it, this)
            }

        })
        return binding.root
    }


    // Setting Search Filters
    val searchResposne = apiResponse
    var queryText:String ?= null
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.menu_search_home)
        val searchView = item?.actionView as SearchView
        binding.homeRecylerView.adapter = HomeRecylerViewAdapter(searchResposne, this)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                queryText = newText!!
                searchResposne.clear()
                binding.homeRecylerView.adapter =
                    HomeRecylerViewAdapter(searchResposne, this@HomeFragment)
                binding.homeRecylerView.adapter?.notifyDataSetChanged()
                val lowercaseAllQuery = newText.lowercase()
                if (lowercaseAllQuery.isNotEmpty()) {
                    apiResponse.forEach {
                        if (it.name.lowercase().contains(lowercaseAllQuery))
                            searchResposne.add(it)
                        else if ((it.symbol.lowercase().contains(lowercaseAllQuery)))
                            searchResposne.add(it)
                    }
                } else {
                    searchResposne.addAll(apiResponse)
                }
                binding.homeRecylerView.adapter?.notifyDataSetChanged()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClick(position: Int) {
            // Setting argument
            // Replacing Fragment
            if (queryText.isNullOrBlank()) {
                viewModel.marketCoinResponse = apiResponse[position]
            } else {
                viewModel.marketCoinResponse = searchResposne[position]
            }
            findNavController().navigate(R.id.action_homeFragment_to_singleCoinDetail)

    }


}