package com.samarth.cryptozee.ui.base.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.MarketCoinResponse
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.databinding.HomeFragmentBinding
import com.samarth.cryptozee.ui.adapters.MainHomeRecylerView
import com.samarth.cryptozee.ui.base.viewModelFactory.MainViewModelFactory
import com.samarth.cryptozee.ui.base.viewmodel.MainViewModel
import com.samarth.cryptozee.utils.CONSTANTS.Companion.LOG_TAG

private lateinit var binding: HomeFragmentBinding

class HomeFragment : Fragment() {
    private  var apiResponse: MarketCoinResponse = MarketCoinResponse()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        binding.homeRecylerView.layoutManager = LinearLayoutManager(context)
        val repository = Repository()
        val viewModel = MainViewModelFactory(repository)
        val viewModelFactory = ViewModelProvider(this, viewModel).get(MainViewModel::class.java)
        viewModelFactory.getAllCoin()
        viewModelFactory.allCoinResponse.observe(viewLifecycleOwner, {
            it?.let {
                Log.d(LOG_TAG, it.toString())
                apiResponse = it
                binding.homeRecylerView.adapter = MainHomeRecylerView(it)
            }

        })
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.menu_search_home)
        val searchView = item?.actionView as SearchView
        val searchResposne = apiResponse
        binding.homeRecylerView.adapter = MainHomeRecylerView(searchResposne)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                searchResposne.clear()
                binding.homeRecylerView.adapter = MainHomeRecylerView(searchResposne)
                binding.homeRecylerView.adapter?.notifyDataSetChanged()
                val lowercaseAllQuery = newText?.lowercase()
                if (lowercaseAllQuery!!.isNotEmpty()) {
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
}