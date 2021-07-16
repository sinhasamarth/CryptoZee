package com.samarth.cryptozee

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samarth.cryptozee.databinding.ActivityMainBinding
import android.graphics.Color

import android.graphics.drawable.ColorDrawable


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarColor()
    }

    private fun setActionBarColor() {
        if(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES){
            val actionBar = supportActionBar
            val colorDrawable = ColorDrawable(Color.parseColor("#001434"))
            actionBar?.setBackgroundDrawable(colorDrawable)
        }
    }

    // Implementation of Search Filter
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.search_menu, menu)
//        val item = menu?.findItem(R.id.menu_search_home)
//        val searchView = item?.actionView as SearchView
//        val searchResposne = apiResponse
//        binding.homeRecylerView.adapter = MainHomeRecylerView(searchResposne)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?) = false
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onQueryTextChange(newText: String?): Boolean {
//                searchResposne.clear()
//                binding.homeRecylerView.adapter = MainHomeRecylerView(searchResposne)
//                binding.homeRecylerView.adapter?.notifyDataSetChanged()
//                val lowercaseAllQuery = newText?.lowercase()
//                if (lowercaseAllQuery!!.isNotEmpty()) {
//                    apiResponse.forEach {
//                        if(it.name.lowercase().contains(lowercaseAllQuery))
//                            searchResposne.add(it)
//                        else if((it.symbol.lowercase().contains(lowercaseAllQuery)))
//                                searchResposne.add(it)
//                    }
//                }
//                else{
//                    searchResposne.addAll(apiResponse)
//                }
//                binding.homeRecylerView.adapter?.notifyDataSetChanged()
//                return false
//            }
//
//        })
//        return super.onCreateOptionsMenu(menu)
//    }
}