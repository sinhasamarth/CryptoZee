package com.samarth.cryptozee

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.samarth.cryptozee.data.model.MarketCoinResponse
import com.samarth.cryptozee.data.model.MarketCoinResponseItem
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.databinding.ActivityMainBinding
import com.samarth.cryptozee.ui.adapters.MainHomeRecylerView
import com.samarth.cryptozee.ui.base.viewModelFactory.MainViewModelFactory
import com.samarth.cryptozee.ui.base.viewmodel.MainViewModel


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var apiResponse = MarketCoinResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecylerView()
        val repository = Repository()
        val viewModel = MainViewModelFactory(repository)
        val viewModelFactory = ViewModelProvider(this, viewModel).get(MainViewModel::class.java)
        viewModelFactory.getAllCoin()
        viewModelFactory.allCoinResponse.observe(this, {
            it?.let {
                apiResponse = it
                binding.homeRecylerView.adapter = MainHomeRecylerView(it)

            }

        })
    }

    private fun setRecylerView() {
        binding.homeRecylerView.layoutManager = LinearLayoutManager(this)
    }

    // Implementation of Search Filter
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.serach_menu, menu)
        val item = menu?.findItem(R.id.menu_search_home)
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
                        if(it.name.lowercase().contains(lowercaseAllQuery))
                            searchResposne.add(it)
                        else if((it.symbol.lowercase().contains(lowercaseAllQuery)))
                                searchResposne.add(it)
                    }
                }
                else{
                    searchResposne.addAll(apiResponse)
                }
                binding.homeRecylerView.adapter?.notifyDataSetChanged()
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}